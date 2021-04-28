# JSON-CSV-Converter
Program converting JSON/CSV files into xml usable by Microsoft Flight Simulator. User interaction in the command line.

***********************************
* POIOSM2FS JSON / CSV Converter  *
***********************************


How to use:

-j (json_file_path) selects a JSON file to use
-c (csv_file_path) selects a CSV file to use
-s (Integer) selects the interval between chosen nodes
-l (String) alows to add a label in front of element's name
-w (String) specifies the owner
-a (Double) specifies the altitude
-o (filename) allows the user to choose the output file
--rn removes lines with names made up of nonlatin characters
--re removes lines with empty names

Example:

java -jar "JSONRivers.jar" -c ruinsplus.csv -l Ruins -w Winhour -a 356.7890 -o ruins -s 20 --rn --re
java -jar JSONRivers.jar -s 25 -j rzeki_IL.json -l Rzeki -w Winhour -a 421.3358 -o rzeki
