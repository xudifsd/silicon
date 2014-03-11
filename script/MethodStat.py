__author__ = 'rockrollroger'

import os
import re
from operator import itemgetter

work_dir = '/root'


def method_stat(apk_list):
    result_dir = work_dir+'/result_stat'
    if not os.path.exists(result_dir):
        os.makedirs(result_dir)

    result_all = open(result_dir+'/MethodStaResAll.txt', 'a')
    result_detail = open(result_dir+'/MethodStaResDet.txt', 'a')
    instruction_file = open('/root/carbon/docs/DalvikInsrtuctions.txt')
    logfile = open(work_dir+'/result_stat/MethodStaLog.txt', 'a')

    method_cal_dic = {}
    apk_cal_dic = {}

    reg_method_start = re.compile(r'^\.method.*')
    reg_method_end = re.compile(r'^\.end method$')

    for each_instruction in instruction_file:
        each_instruction = each_instruction.strip('\n')
        each_instruction = each_instruction.strip()
        method_cal_dic.update({each_instruction: 0})
    for apk_name in apk_list:

        method_num_dic = {}
        method_num = 0
        instruction_num_all = 0
        flag = False

        if apk_name[-3:] == 'apk':
            smali_dir = work_dir+'/result' + '/' + apk_name[:-4] + '/' + apk_name + '.smali'
            try:
                smali_file = open(smali_dir, 'r')
            except IOError:
                logfile.writelines(apk_name+' is missing\n')
            for eachLine in smali_file:
                eachLine = eachLine.strip()
                eachLine = eachLine.strip('\n')

                is_start = reg_method_start.match(eachLine)
                is_end = reg_method_end.match(eachLine)

                if is_end:
                    flag = False
                    method_num_dic.update({apk_name + '_method_' + str(method_num): instruction_num})
                    instruction_num = 0
                if flag:
                    op = eachLine.split(' ', 1)[0]
                    if op in method_cal_dic:
                        instruction_num += 1
                if is_start:
                    flag = True
                    method_num += 1
                    instruction_num = 0

            for method_name, method_ins_num in method_num_dic.items():
                instruction_num_all += method_ins_num
                result_detail.write(method_name+', '+str(method_ins_num)+'\n')

            apk_cal_dic.update({apk_name: int(instruction_num_all/method_num)})
            logfile.writelines(apk_name+' is good\n')
            smali_file.close()

    for apk_name, apk_ins_num in apk_cal_dic.items():
        result_all.write(apk_name+', ' + str(apk_ins_num) + '\n')
    result_all.close()
    result_detail.close()
    logfile.close()


def main():
    apk_dir = work_dir+'/apks'
    catalog_list = os.walk(apk_dir).next()[1]
    for catalog in catalog_list:
        apk_list = os.listdir(apk_dir+'/'+catalog)
        method_stat(apk_list)


if __name__ == "__main__":
    main()