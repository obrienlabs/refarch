
aws s3 mb s3://cf-uswest1-packet-global --region=us-west-1
aws cloudformation deploy --template-file aws-vpc.template.yaml --parameter-overrides AvailabilityZones=us-west-1b,us-west-1c  --stack-name vpc-aws --region=us-west-1 --s3-bucket cf-uswest1-packet-global
