/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.go.domain.BaseCollection;
import com.thoughtworks.go.domain.ConfigErrors;
import com.thoughtworks.go.util.StringUtil;

@ConfigTag("tabs")
@ConfigCollection(Tab.class)
public class Tabs extends BaseCollection<Tab> implements Validatable, ParamsAttributeAware {
    private final ConfigErrors configErrors = new ConfigErrors();

    public ConfigErrors errors() {
        return configErrors;
    }

    public void addError(String fieldName, String message) {
        configErrors.add(fieldName, message);
    }

    public void validate(ValidationContext validationContext) {
        ArrayList<Tab> visitedTabs = new ArrayList<Tab>();
        for (Tab tab : this) {
            tab.validateTabNameUniqueness(visitedTabs);
        }
    }

    public void setConfigAttributes(Object attributes) {
        this.clear();
        if (attributes != null) {
            for (Map attributeMap : (List<Map>) attributes) {
                String tabName = (String) attributeMap.get(Tab.NAME);
                String path = (String) attributeMap.get(Tab.PATH);
                if (StringUtil.isBlank(tabName) && StringUtil.isBlank(path)) {
                    continue;
                }
                this.add(new Tab(tabName, path));
            }
        }
    }
}
