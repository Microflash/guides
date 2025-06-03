import boto3

s3 = boto3.client("s3")
secretsmanager = boto3.client("secretsmanager")
