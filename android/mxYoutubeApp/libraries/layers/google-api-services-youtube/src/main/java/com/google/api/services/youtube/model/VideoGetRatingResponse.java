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
 * Model definition for VideoGetRatingResponse.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the YouTube Data API. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class VideoGetRatingResponse extends com.google.api.client.json.GenericJson {

  /**
   * Etag of this resource.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String etag;

  /**
   * A list of ratings that match the request criteria.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<VideoRating> items;

  /**
   * Identifies what kind of resource this is. Value: the fixed string
   * "youtube#videoGetRatingResponse".
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String kind;

  /**
   * Etag of this resource.
   * @return value or {@code null} for none
   */
  public java.lang.String getEtag() {
    return etag;
  }

  /**
   * Etag of this resource.
   * @param etag etag or {@code null} for none
   */
  public VideoGetRatingResponse setEtag(java.lang.String etag) {
    this.etag = etag;
    return this;
  }

  /**
   * A list of ratings that match the request criteria.
   * @return value or {@code null} for none
   */
  public java.util.List<VideoRating> getItems() {
    return items;
  }

  /**
   * A list of ratings that match the request criteria.
   * @param items items or {@code null} for none
   */
  public VideoGetRatingResponse setItems(java.util.List<VideoRating> items) {
    this.items = items;
    return this;
  }

  /**
   * Identifies what kind of resource this is. Value: the fixed string
   * "youtube#videoGetRatingResponse".
   * @return value or {@code null} for none
   */
  public java.lang.String getKind() {
    return kind;
  }

  /**
   * Identifies what kind of resource this is. Value: the fixed string
   * "youtube#videoGetRatingResponse".
   * @param kind kind or {@code null} for none
   */
  public VideoGetRatingResponse setKind(java.lang.String kind) {
    this.kind = kind;
    return this;
  }

  @Override
  public VideoGetRatingResponse set(String fieldName, Object value) {
    return (VideoGetRatingResponse) super.set(fieldName, value);
  }

  @Override
  public VideoGetRatingResponse clone() {
    return (VideoGetRatingResponse) super.clone();
  }

}
