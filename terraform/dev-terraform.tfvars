#aws_access_key = ""
#aws_secret_key = ""
region             = "eu-west-1"
name               = "ec2-vs-fargate"
environment        = "test"
availability_zones = ["eu-west-1a", "eu-west-1b"]
public_subnets     = ["10.0.16.0/20", "10.0.48.0/20"]
cidr               = "10.0.0.0/16"

#SG
container_port        = 8080
container_environment = [
  {
    name  = "LOG_LEVEL",
    value = "DEBUG"
  }
]