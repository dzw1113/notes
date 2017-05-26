#!/bin/bash

# set the path to nginx log files
log_files_path="/mnt/nginx/kmall/"
log_files_dir=${log_files_path}

#set nginx log files you want to cut
log_files_name=(visit )
#set the path to nginx.
nginx_sbin="/usr/sbin/nginx"
#Set how long you want to save
save_days=30






############################################
#Please do not modify the following script #
############################################


log_files_num=${#log_files_name[@]}

echo $log_files_num
#cut nginx log files
for((i=0;i<$log_files_num;i++));do
mv ${log_files_path}${log_files_name[i]}.log ${log_files_dir}${log_files_name[i]}.log_$(date -d "yesterday" +"%Y-%m-%d")
done


#delete 30 days ago nginx log files
find $log_files_path -mtime +$save_days -exec rm -rf {} \; 


$nginx_sbin -s reload




