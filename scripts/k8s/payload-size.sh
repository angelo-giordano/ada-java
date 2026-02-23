#!/bin/bash

host="https://biblioteca-ada.com:30821/api"

echo "Testando o Tamanho do Payload"
echo "Url: $host/users"
res=$(curl -sk -X POST -F "file=@../output.dat" -w "\nHTTP Status: %{http_code}\n" "$host/users")
echo "$res"
