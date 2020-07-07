file="src/main/resources/lexemes.txt"
args=("$@")
SIZE=${#args[@]}
for ((i = 0; i < SIZE; i++)); do
  echo ${args[${i}]} >>$file
done
