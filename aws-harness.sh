#!/bin/sh -e

: ${AWS_ACCESS_KEY_ID:?}
: ${AWS_SECRET_ACCESS_KEY:?}
: ${AWS_DEFAULT_REGION:?}

: ${IMAGE_ID:=ami-b5a7ea85}
: ${INSTANCE_TYPE:=c3.xlarge}
: ${SECURITY_GROUP:=default}
: ${KEY_NAME:=glowroot-benchmark}
: ${PRIVATE_KEY_FILE:=$HOME/.ssh/glowroot-benchmark.pem}
: ${CLOCKSOURCE:=tsc}

echo building microbenchmarks jar...
mvn clean package

echo creating instance ...
# ssd root volume (volume type gp2)
instance_id=`aws ec2 run-instances --image-id $IMAGE_ID --count 1 --instance-type $INSTANCE_TYPE --key-name $KEY_NAME --security-groups $SECURITY_GROUP | grep InstanceId | cut -d '"' -f4`

# suppress stdout (but not stderr)
aws ec2 create-tags --resources $instance_id --tags Key=Name,Value=baseline-microbenchmark > /dev/null

echo instance created: $instance_id

while
  public_dns_name=`aws ec2 describe-instances --instance-ids $instance_id --filters Name=instance-state-name,Values=running --query 'Reservations[].Instances[].PublicDnsName' --output text`
  [ ! $public_dns_name ]
do
  echo waiting for instance to start ...
  sleep 1
done

echo instance started: $public_dns_name

while
  # intentionally suppress both stdout and stderr
  ssh -i $PRIVATE_KEY_FILE -o StrictHostKeyChecking=no ec2-user@$public_dns_name echo &> /dev/null
  [ "$?" != "0" ]
do
  echo waiting for sshd to start ...
  sleep 1
done

setup_script="
sudo yum -y install java-1.8.0-openjdk-devel
echo 2 | sudo alternatives --config java
echo $CLOCKSOURCE | sudo tee /sys/devices/system/clocksource/clocksource0/current_clocksource > /dev/null
"

scp -i $PRIVATE_KEY_FILE -o StrictHostKeyChecking=no target/microbenchmarks.jar ec2-user@$public_dns_name:.

# -tt is to force a tty which is needed to run sudo
ssh -tt -i $PRIVATE_KEY_FILE -o StrictHostKeyChecking=no ec2-user@$public_dns_name "$setup_script"

# not adding StringSwitch benchmark to README, it was primarily for https://github.com/immutables/immutables/pull/43
ssh -i $PRIVATE_KEY_FILE -o StrictHostKeyChecking=no ec2-user@$public_dns_name "java -jar microbenchmarks.jar -e StringSwitch $@"

echo terminating instance ...
aws ec2 terminate-instances --instance-ids $instance_id > /dev/null
