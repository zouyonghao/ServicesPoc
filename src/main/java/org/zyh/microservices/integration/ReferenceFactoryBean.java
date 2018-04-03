/*
 *  CMB Confidential
 *
 *  Copyright (C) 2016 China Merchants Bank Co., Ltd. All rights reserved.
 *
 *  No part of this file may be reproduced or transmitted in any form or by any
 *  means, electronic, mechanical, photocopying, recording, or otherwise, without
 *  prior written permission of China Merchants Bank Co., Ltd.
 */

package org.zyh.microservices.integration;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.zyh.microservices.poc.protocol.Protocol;

import java.util.Map;

/**
 * @author z72069
 */
public class ReferenceFactoryBean extends AbstractFactoryBean<Object> {

    private Class<?> realClass;
//    private Duty duties;
    private Map<String, Object> context;
    private String protocol;

    public Class<?> getRealClass() {
        return realClass;
    }

    public void setRealClass(Class<?> realClass) {
        this.realClass = realClass;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

//    public Duty getDuties() {
//        return duties;
//    }
//
//    public void setDuties(Duty duties) {
//        this.duties = duties;
//    }

    @Override
    public Class<?> getObjectType() {
        return realClass;
    }

    @Override
    protected Object createInstance() throws Exception {
//        ApplicationInformation information = getBeanFactory().getBean(ApplicationInformation.class);
//        Protocol protocolInstance = ProtocolLazyLoadingHelper.getProtocol(getBeanFactory(), this.protocol);
//        String application = information.getApplication();
//        List<String> groups = information.getGroups();
//        String callside = InvokerHelper.REFERENCE;
//        ProtocolHelper.initialProtocolContext(context, realClass, protocolInstance, application, groups, callside);
//        return protocolInstance.refer(realClass, duties, context);
        Protocol protocol = getBeanFactory().getBean(Protocol.class);
        return protocol.reference(realClass);
    }

}
