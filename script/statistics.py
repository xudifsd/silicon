__author__ = 'rockrollroger'

import os
#import re
from operator import itemgetter

work_dir = '/root'


def instruction_stat(apk_list):
    instruction_file = open('/root/carbon/docs/DalvikInsrtuctions.txt')
    logfile = open(work_dir+'/result_stat/log.txt', 'a')
    freq_dic = {}
#    reg_instruction = re.compile(r'^[a-zA-z][\-\/|\w]+')
    for each_instruction in instruction_file:
        each_instruction = each_instruction.strip('\n')
        each_instruction = each_instruction.strip()
        freq_dic.update({each_instruction: 0})
    for apk_name in apk_list:
        if apk_name[-3:] == 'apk':
            smali_dir = work_dir+'/result' + '/' + apk_name[:-4] + '/' + apk_name + '.smali'
            try:
                smali_file = open(smali_dir, 'r')
            except IOError:
                logfile.writelines(apk_name+' is missing\n')
            for eachLine in smali_file:
                try:
                    eachLine=eachLine.strip()
                    eachLine=eachLine.strip('\n')
                    op = eachLine.split(' ', 1)[0]
                    op = op.strip()
                    op = op.strip('\n')
                    if op in freq_dic:
                        freq_dic[op] += 1
                except ValueError:
                    pass
            logfile.writelines(apk_name+' is good\n')
            smali_file.close()
    logfile.close()
    return freq_dic


def main():
    result_dir = work_dir+'/result_stat'
    if not os.path.exists(result_dir):
        os.makedirs(result_dir)
    apk_dir = work_dir+'/apks'
    catalog_list = os.walk(apk_dir).next()[1]
    result = open(result_dir+'/statisticsResult.txt', 'w')
    for catalog in catalog_list:
        apk_list = os.listdir(apk_dir+'/'+catalog)
        freq_list = instruction_stat(apk_list).items()
        freq_list.sort(key=itemgetter(1), reverse=True)
        result.write('--------------------------------'+catalog+'----------------------------------\n')
        for instruction, frq in freq_list:
            result.write(instruction+', '+str(frq)+'\n')


if __name__ == "__main__":
    main()