# start.sh
if [ "$RATINGS_ENABLED" = "true" ]; then
  exec java -jar /app/app.jar
else
  echo "Ratings service is disabled. Exiting."
  exit 0
fi
