file="src/main/resources/config.properties"
echo "" >$file
if [ $# -ge 2 ]; then
  echo "seed=$1" >>$file
  echo "lexemes=$2" >>$file
fi
if [ $# -ge 3 ]; then
  echo "max.depth=$3" >>$file
fi
if [ $# -ge 4 ]; then
  echo "max.pages=$4" >>$file
fi
