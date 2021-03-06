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
 * Detailed settings of a broadcast.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the YouTube Data API. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class LiveBroadcastContentDetails extends com.google.api.client.json.GenericJson {

  /**
   * This value uniquely identifies the live stream bound to the broadcast.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String boundStreamId;

  /**
   * This setting indicates whether closed captioning is enabled for this broadcast. The ingestion
   * URL of the closed captions is returned through the liveStreams API.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean enableClosedCaptions;

  /**
   * This setting indicates whether YouTube should enable content encryption for the broadcast.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean enableContentEncryption;

  /**
   * This setting determines whether viewers can access DVR controls while watching the video. DVR
   * controls enable the viewer to control the video playback experience by pausing, rewinding, or
   * fast forwarding content. The default value for this property is true.
   *
   * Important: You must set the value to true and also set the enableArchive property's value to
   * true if you want to make playback available immediately after the broadcast ends.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean enableDvr;

  /**
   * This setting indicates whether the broadcast video can be played in an embedded player. If you
   * choose to archive the video (using the enableArchive property), this setting will also apply to
   * the archived video.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean enableEmbed;

  /**
   * The monitorStream object contains information about the monitor stream, which the broadcaster
   * can use to review the event content before the broadcast stream is shown publicly.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private MonitorStreamInfo monitorStream;

  /**
   * Automatically start recording after the event goes live. The default value for this property is
   * true.
   *
   * Important: You must also set the enableDvr property's value to true if you want the playback to
   * be available immediately after the broadcast ends. If you set this property's value to true but
   * do not also set the enableDvr property to true, there may be a delay of around one day before
   * the archived video will be available for playback.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean recordFromStart;

  /**
   * This setting indicates whether the broadcast should automatically begin with an in-stream slate
   * when you update the broadcast's status to live. After updating the status, you then need to
   * send a liveCuepoints.insert request that sets the cuepoint's eventState to end to remove the
   * in-stream slate and make your broadcast stream visible to viewers.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean startWithSlate;

  /**
   * This value uniquely identifies the live stream bound to the broadcast.
   * @return value or {@code null} for none
   */
  public java.lang.String getBoundStreamId() {
    return boundStreamId;
  }

  /**
   * This value uniquely identifies the live stream bound to the broadcast.
   * @param boundStreamId boundStreamId or {@code null} for none
   */
  public LiveBroadcastContentDetails setBoundStreamId(java.lang.String boundStreamId) {
    this.boundStreamId = boundStreamId;
    return this;
  }

  /**
   * This setting indicates whether closed captioning is enabled for this broadcast. The ingestion
   * URL of the closed captions is returned through the liveStreams API.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEnableClosedCaptions() {
    return enableClosedCaptions;
  }

  /**
   * This setting indicates whether closed captioning is enabled for this broadcast. The ingestion
   * URL of the closed captions is returned through the liveStreams API.
   * @param enableClosedCaptions enableClosedCaptions or {@code null} for none
   */
  public LiveBroadcastContentDetails setEnableClosedCaptions(java.lang.Boolean enableClosedCaptions) {
    this.enableClosedCaptions = enableClosedCaptions;
    return this;
  }

  /**
   * This setting indicates whether YouTube should enable content encryption for the broadcast.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEnableContentEncryption() {
    return enableContentEncryption;
  }

  /**
   * This setting indicates whether YouTube should enable content encryption for the broadcast.
   * @param enableContentEncryption enableContentEncryption or {@code null} for none
   */
  public LiveBroadcastContentDetails setEnableContentEncryption(java.lang.Boolean enableContentEncryption) {
    this.enableContentEncryption = enableContentEncryption;
    return this;
  }

  /**
   * This setting determines whether viewers can access DVR controls while watching the video. DVR
   * controls enable the viewer to control the video playback experience by pausing, rewinding, or
   * fast forwarding content. The default value for this property is true.
   *
   * Important: You must set the value to true and also set the enableArchive property's value to
   * true if you want to make playback available immediately after the broadcast ends.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEnableDvr() {
    return enableDvr;
  }

  /**
   * This setting determines whether viewers can access DVR controls while watching the video. DVR
   * controls enable the viewer to control the video playback experience by pausing, rewinding, or
   * fast forwarding content. The default value for this property is true.
   *
   * Important: You must set the value to true and also set the enableArchive property's value to
   * true if you want to make playback available immediately after the broadcast ends.
   * @param enableDvr enableDvr or {@code null} for none
   */
  public LiveBroadcastContentDetails setEnableDvr(java.lang.Boolean enableDvr) {
    this.enableDvr = enableDvr;
    return this;
  }

  /**
   * This setting indicates whether the broadcast video can be played in an embedded player. If you
   * choose to archive the video (using the enableArchive property), this setting will also apply to
   * the archived video.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getEnableEmbed() {
    return enableEmbed;
  }

  /**
   * This setting indicates whether the broadcast video can be played in an embedded player. If you
   * choose to archive the video (using the enableArchive property), this setting will also apply to
   * the archived video.
   * @param enableEmbed enableEmbed or {@code null} for none
   */
  public LiveBroadcastContentDetails setEnableEmbed(java.lang.Boolean enableEmbed) {
    this.enableEmbed = enableEmbed;
    return this;
  }

  /**
   * The monitorStream object contains information about the monitor stream, which the broadcaster
   * can use to review the event content before the broadcast stream is shown publicly.
   * @return value or {@code null} for none
   */
  public MonitorStreamInfo getMonitorStream() {
    return monitorStream;
  }

  /**
   * The monitorStream object contains information about the monitor stream, which the broadcaster
   * can use to review the event content before the broadcast stream is shown publicly.
   * @param monitorStream monitorStream or {@code null} for none
   */
  public LiveBroadcastContentDetails setMonitorStream(MonitorStreamInfo monitorStream) {
    this.monitorStream = monitorStream;
    return this;
  }

  /**
   * Automatically start recording after the event goes live. The default value for this property is
   * true.
   *
   * Important: You must also set the enableDvr property's value to true if you want the playback to
   * be available immediately after the broadcast ends. If you set this property's value to true but
   * do not also set the enableDvr property to true, there may be a delay of around one day before
   * the archived video will be available for playback.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getRecordFromStart() {
    return recordFromStart;
  }

  /**
   * Automatically start recording after the event goes live. The default value for this property is
   * true.
   *
   * Important: You must also set the enableDvr property's value to true if you want the playback to
   * be available immediately after the broadcast ends. If you set this property's value to true but
   * do not also set the enableDvr property to true, there may be a delay of around one day before
   * the archived video will be available for playback.
   * @param recordFromStart recordFromStart or {@code null} for none
   */
  public LiveBroadcastContentDetails setRecordFromStart(java.lang.Boolean recordFromStart) {
    this.recordFromStart = recordFromStart;
    return this;
  }

  /**
   * This setting indicates whether the broadcast should automatically begin with an in-stream slate
   * when you update the broadcast's status to live. After updating the status, you then need to
   * send a liveCuepoints.insert request that sets the cuepoint's eventState to end to remove the
   * in-stream slate and make your broadcast stream visible to viewers.
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getStartWithSlate() {
    return startWithSlate;
  }

  /**
   * This setting indicates whether the broadcast should automatically begin with an in-stream slate
   * when you update the broadcast's status to live. After updating the status, you then need to
   * send a liveCuepoints.insert request that sets the cuepoint's eventState to end to remove the
   * in-stream slate and make your broadcast stream visible to viewers.
   * @param startWithSlate startWithSlate or {@code null} for none
   */
  public LiveBroadcastContentDetails setStartWithSlate(java.lang.Boolean startWithSlate) {
    this.startWithSlate = startWithSlate;
    return this;
  }

  @Override
  public LiveBroadcastContentDetails set(String fieldName, Object value) {
    return (LiveBroadcastContentDetails) super.set(fieldName, value);
  }

  @Override
  public LiveBroadcastContentDetails clone() {
    return (LiveBroadcastContentDetails) super.clone();
  }

}
