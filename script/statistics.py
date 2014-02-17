__author__ = 'rockrollroger'

import os
import re
from operator import itemgetter


def instruction_stat(apk_list):
    work_dir = '/root/result'
    logfile = open('/root/result_stat/log.txt', 'a')
    freq_dic = {}
    reg_instruction = re.compile(r'^[a-zA-z][\-\/|\w]+')
    for apk_name in apk_list:
        if apk_name[-3:] == 'apk':
            smali_dir = work_dir + '/' + apk_name[:-4] + '/' + apk_name + '.smali'
            try:
                smali_file = open(smali_dir, 'r')
            except IOError:
                logfile.writelines(apk_name+'   is missing\n')
            for eachLine in smali_file:
                try:
                    (op, rest) = eachLine.split(' ', 1)
                    is_instruction = reg_instruction.match(op)
                    if is_instruction:
                        try:
                            freq_dic[op] += 1
                        except KeyError:
                            freq_dic[op] = 1
                except ValueError:
                    pass
            logfile.writelines(apk_name+' is good\n')
            smali_file.close()
    logfile.close()
    return freq_dic


def main():
    result_dir = '/root/result_stat'
    if not os.path.exists(result_dir):
        os.makedirs(result_dir)
    apk_dir = '/root/apks'
    catalog_list = os.walk(apk_dir).next()[1]
    result = open('/root/result_stat/statisticsResult.txt', 'w')
    for catalog in catalog_list:
        apk_list = os.listdir(apk_dir+'/'+catalog)
        freq_list = instruction_stat(apk_list).items()
        freq_list.sort(key=itemgetter(1), reverse=True)
        result.write('--------------------------------'+catalog+'----------------------------------\n')
        for instruction, frq in freq_list:
            result.write(instruction+', '+str(frq)+'\n')


if __name__ == "__main__":
    main()