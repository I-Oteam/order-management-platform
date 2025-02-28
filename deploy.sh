#!/bin/bash

REGION="ap-northeast-2"
ECR_REPOSITORY="omp"
IMAGE_TAG="latest"
AWS_ACCOUNT_ID=484907500150

cd ~/backend/
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com
docker compose pull
docker compose up -d
echo ':::::::::docker compose restart:::::::::'
docker image prune -f
