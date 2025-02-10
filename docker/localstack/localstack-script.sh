#!/bin/bash

AWS_REGION=eu-central-1
SQS_QUEUE_VISIBILITY_TIMEOUT=900

declare QUEUES=(
  "meeting-acknowledgments"
)

create_sqs_queue() {
  local queue_name=$1
  awslocal sqs create-queue \
    --queue-name "$queue_name" \
    --region "$AWS_REGION" \
    --attributes VisibilityTimeout=$SQS_QUEUE_VISIBILITY_TIMEOUT
}

for queue_name in "${QUEUES[@]}"; do
  create_sqs_queue "$queue_name"
done
