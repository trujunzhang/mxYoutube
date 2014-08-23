/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.auth.oauth;

import com.google.api.client.util.Beta;

import java.security.GeneralSecurityException;

/**
 * {@link Beta} <br/>
 * OAuth signature method.
 *
 * @since 1.0
 * @author Yaniv Inbar
 */
@Beta
public interface OAuthSigner {

  /** Returns the signature method. */
  String getSignatureMethod();

  /**
   * Returns the signature computed from the given signature base string.
   *
   * @throws GeneralSecurityException general security exception
   */
  String computeSignature(String signatureBaseString) throws GeneralSecurityException;
}
