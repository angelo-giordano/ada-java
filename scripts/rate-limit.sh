#!/bin/bash

endpoint=$1
req_qt=$2
host="localhost:80/api"

echo "Testando o Rate Limit do Nginx"
echo "Url: $host/$endpoint"

for i in $(seq 1 $req_qt)
do
    echo "Requisicao $i"
    curl -s "$host/$endpoint" &>/dev/null
done
