# JSON-CSV-Converter
Program converting JSON/CSV files, which contain information about interesting locations into xml with POI elements usable by Microsoft Flight Simulator (2020). User interaction in the command line. Currently working on additional functionality.

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
--rst (Integer) removes elements with type 'Temple' which have number of tags less than or equal to the chosen threshold
--rsv (Integer) removes elements with type 'Village' which have number of tags less than or equal to the chosen threshold
-t makes folder containing subfolders [texture] with .png files for textures and [model] for future models
--txw (Integer) sets width for the textures (default = 350)


Example:

java -jar "JSONRivers.jar" -c ruinsplus.csv -l Ruins -w Winhour -a 356.7890 -o ruins -s 20 --rn --re
java -jar JSONRivers.jar -s 25 -j rzeki_IL.json -l Rzeki -w Winhour -a 421.3358 -o rzeki
java -jar POIOSM2FS.jar --ja all.json --rst 5 --rsv 10 -t\n
