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
 * Basic details about a playlist, including title, description and thumbnails.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the YouTube Data API. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PlaylistSnippet extends com.google.api.client.json.GenericJson {

  /**
   * The ID that YouTube uses to uniquely identify the channel that published the playlist.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String channelId;

  /**
   * The channel title of the channel that the video belongs to.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String channelTitle;

  /**
   * The playlist's description.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The date and time that the playlist was created. The value is specified in ISO 8601 (YYYY-MM-
   * DDThh:mm:ss.sZ) format.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime publishedAt;

  /**
   * Keyword tags associated with the playlist.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> tags;

  /**
   * A map of thumbnail images associated with the playlist. For each object in the map, the key is
   * the name of the thumbnail image, and the value is an object that contains other information
   * about the thumbnail.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private ThumbnailDetails thumbnails;

  /**
   * The playlist's title.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

  /**
   * The ID that YouTube uses to uniquely identify the channel that published the playlist.
   * @return value or {@code null} for none
   */
  public java.lang.String getChannelId() {
    return channelId;
  }

  /**
   * The ID that YouTube uses to uniquely identify the channel that published the playlist.
   * @param channelId channelId or {@code null} for none
   */
  public PlaylistSnippet setChannelId(java.lang.String channelId) {
    this.channelId = channelId;
    return this;
  }

  /**
   * The channel title of the channel that the video belongs to.
   * @return value or {@code null} for none
   */
  public java.lang.String getChannelTitle() {
    return channelTitle;
  }

  /**
   * The channel title of the channel that the video belongs to.
   * @param channelTitle channelTitle or {@code null} for none
   */
  public PlaylistSnippet setChannelTitle(java.lang.String channelTitle) {
    this.channelTitle = channelTitle;
    return this;
  }

  /**
   * The playlist's description.
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * The playlist's description.
   * @param description description or {@code null} for none
   */
  public PlaylistSnippet setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * The date and time that the playlist was created. The value is specified in ISO 8601 (YYYY-MM-
   * DDThh:mm:ss.sZ) format.
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getPublishedAt() {
    return publishedAt;
  }

  /**
   * The date and time that the playlist was created. The value is specified in ISO 8601 (YYYY-MM-
   * DDThh:mm:ss.sZ) format.
   * @param publishedAt publishedAt or {@code null} for none
   */
  public PlaylistSnippet setPublishedAt(com.google.api.client.util.DateTime publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  /**
   * Keyword tags associated with the playlist.
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTags() {
    return tags;
  }

  /**
   * Keyword tags associated with the playlist.
   * @param tags tags or {@code null} for none
   */
  public PlaylistSnippet setTags(java.util.List<java.lang.String> tags) {
    this.tags = tags;
    return this;
  }

  /**
   * A map of thumbnail images associated with the playlist. For each object in the map, the key is
   * the name of the thumbnail image, and the value is an object that contains other information
   * about the thumbnail.
   * @return value or {@code null} for none
   */
  public ThumbnailDetails getThumbnails() {
    return thumbnails;
  }

  /**
   * A map of thumbnail images associated with the playlist. For each object in the map, the key is
   * the name of the thumbnail image, and the value is an object that contains other information
   * about the thumbnail.
   * @param thumbnails thumbnails or {@code null} for none
   */
  public PlaylistSnippet setThumbnails(ThumbnailDetails thumbnails) {
    this.thumbnails = thumbnails;
    return this;
  }

  /**
   * The playlist's title.
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * The playlist's title.
   * @param title title or {@code null} for none
   */
  public PlaylistSnippet setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  @Override
  public PlaylistSnippet set(String fieldName, Object value) {
    return (PlaylistSnippet) super.set(fieldName, value);
  }

  @Override
  public PlaylistSnippet clone() {
    return (PlaylistSnippet) super.clone();
  }

}
