/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.qos.command.impl;

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.qos.command.BaseCommand;
import org.apache.dubbo.qos.command.CommandContext;
import org.apache.dubbo.qos.command.annotation.Cmd;
import org.apache.dubbo.qos.textui.TTable;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ConsumerMethodModel;
import org.apache.dubbo.rpc.model.ConsumerModel;
import org.apache.dubbo.rpc.model.ProviderMethodModel;
import org.apache.dubbo.rpc.model.ProviderModel;

import java.util.Collection;

import static org.apache.dubbo.registry.support.ProviderConsumerRegTable.getConsumerAddressNum;
import static org.apache.dubbo.registry.support.ProviderConsumerRegTable.isRegistered;

@Cmd(name = "ls", summary = "ls service", example = {
        "ls [-l]"
})
public class Ls implements BaseCommand {
    @Override
    public String execute(CommandContext commandContext, String[] args) {
        StringBuilder result = new StringBuilder();
        result.append(listProvider(args));
        result.append(listConsumer(args));

        return result.toString();
    }

    public String listProvider(String[] args) {
        boolean listMethod = false;
        if (null != args && args.length > 0 && StringUtils.isNotEmpty(args[0])) {
            listMethod = "-l".contains(args[0]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("As Provider side:").append(System.lineSeparator());
        Collection<ProviderModel> providerModelList = ApplicationModel.allProviderModels();
        TTable tTable = null;

        if (listMethod) {
            tTable = new TTable(new TTable.ColumnDefine[]{
                    new TTable.ColumnDefine(TTable.Align.MIDDLE),
                    new TTable.ColumnDefine(TTable.Align.LEFT),
                    new TTable.ColumnDefine(TTable.Align.MIDDLE)
            });
            tTable.addRow("Provider Service Name", "Method", "PUB");
        } else {
            //Header
            tTable = new TTable(new TTable.ColumnDefine[]{
                    new TTable.ColumnDefine(TTable.Align.MIDDLE),
                    new TTable.ColumnDefine(TTable.Align.MIDDLE)
            });
            tTable.addRow("Provider Service Name", "PUB");
        }

        //Content
        for (ProviderModel providerModel : providerModelList) {
            if (listMethod) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < providerModel.getAllMethods().size(); i++) {
                    ProviderMethodModel method = providerModel.getAllMethods().get(i);
                    sb.append(method.getMethodName()).append("(");
                    if (null != method.getMethodArgTypes() && method.getMethodArgTypes().length > 0) {
                        for (int j = 0; j < method.getMethodArgTypes().length; j++) {
                            sb.append(method.getMethodArgTypes()[j]);
                            if (j != method.getMethodArgTypes().length - 1) {
                                sb.append(",");
                            }
                        }
                    }
                    sb.append(")");
                    if (i < providerModel.getAllMethods().size()) {
                        sb.append(System.lineSeparator());
                    }
                }
                tTable.addRow(providerModel.getServiceName(), sb, isRegistered(providerModel.getServiceName()) ? "Y" : "N");
            } else {
                tTable.addRow(providerModel.getServiceName(), isRegistered(providerModel.getServiceName()) ? "Y" : "N");
            }
        }
        stringBuilder.append(tTable.rendering());

        return stringBuilder.toString();
    }

    public String listConsumer(String [] args) {
        boolean listMethod = false;
        if (null != args && args.length > 0 && StringUtils.isNotEmpty(args[0])) {
            listMethod = "-l".contains(args[0]);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("As Consumer side:" + System.lineSeparator());
        Collection<ConsumerModel> consumerModelList = ApplicationModel.allConsumerModels();
        TTable tTable = null;
        if (listMethod) {
            tTable = new TTable(new TTable.ColumnDefine[]{
                    new TTable.ColumnDefine(TTable.Align.MIDDLE),
                    new TTable.ColumnDefine(TTable.Align.LEFT),
                    new TTable.ColumnDefine(TTable.Align.MIDDLE)
            });
            //Header
            tTable.addRow("Consumer Service Name", "Method", "NUM");
        } else {
            tTable = new TTable(new TTable.ColumnDefine[]{
                    new TTable.ColumnDefine(TTable.Align.MIDDLE),
                    new TTable.ColumnDefine(TTable.Align.MIDDLE)
            });
            //Header
            tTable.addRow("Consumer Service Name", "NUM");
        }

        //Content
        //TODO to calculate consumerAddressNum
        for (ConsumerModel consumerModel : consumerModelList) {
            if (listMethod) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < consumerModel.getAllMethods().size(); i++) {
                    ConsumerMethodModel method = consumerModel.getAllMethods().get(i);
                    sb.append(method.getMethodName()).append("(");
                    if (null != method.getParameterTypes() && method.getParameterTypes().length > 0) {
                        for (int j = 0; j < method.getParameterTypes().length; j++) {
                            sb.append(method.getParameterTypes()[j]);
                            if (j != method.getParameterTypes().length - 1) {
                                sb.append(",");
                            }
                        }
                    }
                    sb.append(")");
                    if (i < consumerModel.getAllMethods().size()) {
                        sb.append(System.lineSeparator());
                    }
                }
                tTable.addRow(consumerModel.getServiceName(), sb, isRegistered(consumerModel.getServiceName()) ? "Y" : "N");
            } else {
                tTable.addRow(consumerModel.getServiceName(), getConsumerAddressNum(consumerModel.getServiceName()));
            }
        }

        stringBuilder.append(tTable.rendering());

        return stringBuilder.toString();
    }
}
