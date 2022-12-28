variable "aws_access_key" {
  default = ""
}
variable "aws_secret_key" {
  default = ""
}

variable "region" {
  type    = string
  default = "us-east-1"
}
variable "name" {
  description = "the name of your stack, e.g. \"demo\""
}

variable "environment" {
  description = "the name of your environment, e.g. \"prod\""
}

variable "cidr" {
  description = "The CIDR block for the VPC."
}

variable "public_subnets" {
  description = "List of public subnets"
}

variable "availability_zones" {
  description = "List of availability zones"
}

variable "container_port" {
  description = "The port where the Docker is exposed"
  default     = 8080
}

variable "container_cpu" {
  default = "512"
}
variable "container_memory" {
  default = "1024"
}
variable "container_image" {
  default = "687133646406.dkr.ecr.eu-west-1.amazonaws.com/ec2-vs-fargate"
}
variable "image_version" {
  description = "docker image version/tag"
}
variable "container_environment" {
  default = "test"
}
variable "service_desired_count" {
  default = "0"
}

variable "health_check_path" {
  default = "/actuator/health"
}