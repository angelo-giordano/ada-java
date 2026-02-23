#!/bin/bash

endpoint=$1
req_qt=$2
host="https://biblioteca-ada.com:30821"

echo "Testando o Rate Limit"
echo "Url: $host/$endpoint"

for i in $(seq 1 $req_qt)
do
    status=$(curl -sk -o /dev/null -w "%{http_code}\n" "$host/$endpoint")
    echo "$i: HTTP Code => $status"
done
