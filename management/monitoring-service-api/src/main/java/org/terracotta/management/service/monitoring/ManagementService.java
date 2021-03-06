/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.terracotta.management.service.monitoring;

import com.tc.classloader.CommonComponent;
import org.terracotta.entity.ClientDescriptor;
import org.terracotta.management.model.call.Parameter;
import org.terracotta.management.model.cluster.Cluster;
import org.terracotta.management.model.context.Context;
import org.terracotta.management.model.message.Message;
import org.terracotta.management.sequence.Sequence;

/**
 * Class used by the active tms entity to monitor the stripe ans send management calls
 *
 * @author Mathieu Carbou
 */
@CommonComponent
public interface ManagementService {

  /**
   * @return the current topology. You must not apply any mutation to the returned object.
   * A cluster is a composition of several clients and stripes, but the returned cluster will only have one stripe: the one we are currently on.
   * The stripe name can be configured.
   * <p>
   * Can be called from active entity only
   */
  Cluster readTopology();

  /**
   * Request a management call from an entity client to another client of the same entity
   * <p>
   * Can be called from active entity only
   *
   * @return An unique identifier for this management call
   */
  String sendManagementCallRequest(ClientDescriptor caller, Context context, String capabilityName, String methodName, Class<?> returnType, Parameter... parameters);

  /**
   * Can be called from active entity only
   *
   * @return The buffer containing all best effort messages, including statistics and notifications.
   */
  ReadOnlyBuffer<Message> createMessageBuffer(int maxBufferSize);

  /**
   * Can be called from active entity only
   *
   * @return The next sequence to use to build a monitoring message
   */
  Sequence nextSequence();

}
