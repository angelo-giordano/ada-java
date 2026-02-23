#!/bin/bash

host="https://biblioteca-ada.com:30821/api"

echo "Testando o Tamanho do Payload"
echo "Url: $host/users"
res=$(curl -ksI -X GET -H "Accept-Encoding: gzip" "$host/api/users" | grep -e "^" -e "content-encoding" --color=always)
echo "$res"
