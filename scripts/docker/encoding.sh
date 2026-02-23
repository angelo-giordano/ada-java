#!/bin/bash

host="https://localhost/api"

echo "Testando o Tamanho do Payload"
echo "Url: $host/users"
res=$(curl -ksI -X GET -H "Accept-Encoding: gzip" "$host/api/users" | grep -e "^" -e "content-encoding" --color=always)
echo "$res"
