
/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jcs.yajcache.lang.annotation;

import java.lang.annotation.*;

/**
 * Annotates what needs to be done.
 *
 * @author Hanson Char
 */
@CopyRightApache
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface TODO {
    /** Summary of what needs to be done. */
    String value() default "";
    /** Details of what needs to be done. */
    String details() default "";
}