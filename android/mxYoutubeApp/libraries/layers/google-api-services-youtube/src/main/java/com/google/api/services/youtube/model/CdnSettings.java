/*
 * Copyright 2010 Google Inc.
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
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-05-23 at 17:34:18 UTC 
 * Modify at your own risk.
 */

package com.google.api.services.youtube.model;

/**
 * Brief description of the live stream cdn settings.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the YouTube Data API. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class CdnSettings extends com.google.api.client.json.GenericJson {

  /**
   * The format of the video stream that you are sending to Youtube.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String format;

  /**
   * The ingestionInfo object contains information that YouTube provides that you need to transmit
   * your RTMP or HTTP stream to YouTube.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private IngestionInfo ingestionInfo;

  /**
   * The method or protocol used to transmit the video stream.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String ingestionType;

  /**
   * The format of the video stream that you are sending to Youtube.
   * @return value or {@code null} for none
   */
  public java.lang.String getFormat() {
    return format;
  }

  /**
   * The format of the video stream that you are sending to Youtube.
   * @param format format or {@code null} for none
   */
  public CdnSettings setFormat(java.lang.String format) {
    this.format = format;
    return this;
  }

  /**
   * The ingestionInfo object contains information that YouTube provides that you need to transmit
   * your RTMP or HTTP stream to YouTube.
   * @return value or {@code null} for none
   */
  public IngestionInfo getIngestionInfo() {
    return ingestionInfo;
  }

  /**
   * The ingestionInfo object contains information that YouTube provides that you need to transmit
   * your RTMP or HTTP stream to YouTube.
   * @param ingestionInfo ingestionInfo or {@code null} for none
   */
  public CdnSettings setIngestionInfo(IngestionInfo ingestionInfo) {
    this.ingestionInfo = ingestionInfo;
    return this;
  }

  /**
   * The method or protocol used to transmit the video stream.
   * @return value or {@code null} for none
   */
  public java.lang.String getIngestionType() {
    return ingestionType;
  }

  /**
   * The method or protocol used to transmit the video stream.
   * @param ingestionType ingestionType or {@code null} for none
   */
  public CdnSettings setIngestionType(java.lang.String ingestionType) {
    this.ingestionType = ingestionType;
    return this;
  }

  @Override
  public CdnSettings set(String fieldName, Object value) {
    return (CdnSettings) super.set(fieldName, value);
  }

  @Override
  public CdnSettings clone() {
    return (CdnSettings) super.clone();
  }

}
