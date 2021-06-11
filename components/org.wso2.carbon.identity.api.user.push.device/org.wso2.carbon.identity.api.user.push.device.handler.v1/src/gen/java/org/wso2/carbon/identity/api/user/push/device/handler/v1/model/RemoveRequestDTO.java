/*
* Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.wso2.carbon.identity.api.user.push.device.handler.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

/**
 * Removing a device by deviceId
 **/

import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;
@ApiModel(description = "Removing a device by deviceId")
public class RemoveRequestDTO  {
  
    private String token;

    /**
    * JWT signed with unique private key containing remove device information
    **/
    public RemoveRequestDTO token(String token) {

        this.token = token;
        return this;
    }
    
    @ApiModelProperty(example = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiI5ZjA3ZTAwOS1lM2MxLTQzMTQtYjg1Yy04NzY4MDc0YzM1NWEiLCJzdWIiOiJhbGFuQGNhcmJvbi5zdXBlciIsImlzcyI6IndzbzJ2ZXJpZnkiLCJhdWQiOiJodHRwczovL2xvY2FsaG9zdDo5NDQzL3QvY2FyYm9uLnN1cGVyLyIsIm5iZiI6MTYxNDY2OTI0MywiZXhwIjoxNjE0NjcyODQzLCJpYXQiOjE2MTQ2NjkyNDMsImNoZyI6ImUwOTg4MTQ4LWIzMzctNGU0MS05NWQ3LWY5YTFmNDNkMDYyMyJ9.i6mh4SSJTfSP2IEyAQR5uZd1y0hHc4is4P66FRiYis5Yewl6k0DRoVXSZWWvGikX4rpqbZj868uBDmqm1CAENB3xF7sx3jdNSrXrVdUW6HsR2uu5Q5eO6OCQg8iZB2B1ylu4R5drTjtTDaSOoQfsNbVjnzI095bfLpAsszGPEPM", required = true, value = "JWT signed with unique private key containing remove device information")
    @JsonProperty("token")
    @Valid
    @NotNull(message = "Property token cannot be null.")

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoveRequestDTO removeRequestDTO = (RemoveRequestDTO) o;
        return Objects.equals(this.token, removeRequestDTO.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class RemoveRequestDTO {\n");
        
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}

