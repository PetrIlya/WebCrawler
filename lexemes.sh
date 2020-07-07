file="src/main/resources/lexemes.txt"
echo >$file
args=("$@")
SIZE=${#args[@]}
for ((i = 0; i < SIZE; i++)); do
  echo ${args[${i}]} >>$file
done
