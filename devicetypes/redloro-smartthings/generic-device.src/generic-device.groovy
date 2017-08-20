/**
 *  SmartThings Device Handler: Generic Device
 *
 *  Author: redloro@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
metadata {
  definition (name: "Generic Device", namespace: "redloro-smartthings", author: "redloro@gmail.com") {

    /**
     * List our capabilties. Doing so adds predefined command(s) which
     * belong to the capability.
     */
    capability "Switch"
    capability "Refresh"
    capability "Polling"

    /**
     * Define all commands, ie, if you have a custom action not
     * covered by a capability, you NEED to define it here or
     * the call will not be made.
     *
     * To call a capability function, just prefix it with the name
     * of the capability, for example, refresh would be "refresh.refresh"
     */
    command "hdmi1"
    command "hdmi2"
    command "hdmi3"
    command "hdmi4"
    command "hdmi5"
    command "play"
    command "pause"
    command "stop"
  }

  /**
   * Define the various tiles and the states that they can be in.
   * The 2nd parameter defines an event which the tile listens to,
   * if received, it tries to map it to a state.
   *
   * You can also use ${currentValue} for the value of the event
   * or ${name} for the name of the event. Just make SURE to use
   * single quotes, otherwise it will only be interpreted at time of
   * launch, instead of every time the event triggers.
   */
  tiles(scale: 2) {
    multiAttributeTile(name:"state", type:"generic", width:6, height:4) {
      tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
        attributeState "on", label:'On', action:"switch.off", icon:"st.Entertainment.entertainment13", backgroundColor:"#79b821", nextState:"off"
        attributeState "off", label:'Off', action:"switch.on", icon:"st.Entertainment.entertainment13", backgroundColor:"#ffffff", nextState:"on"
      }
      tileAttribute ("command", key: "SECONDARY_CONTROL") {
        attributeState "command", label:'${currentValue}'
      }
    }

    // Row 1
    standardTile("hdmi1", "device.hdmi1", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"hdmi1", icon:"st.Electronics.electronics6", label: "HDMI 1", backgroundColor:"#ffffff"
    }
    standardTile("hdmi2", "device.hdmi2", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"hdmi2", icon:"st.Electronics.electronics6", label: "HDMI 2", backgroundColor:"#ffffff"
    }
    standardTile("hdmi3", "device.hdmi3", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"hdmi3", icon:"st.Electronics.electronics6", label: "HDMI 3", backgroundColor:"#ffffff"
    }
    standardTile("hdmi4", "device.hdmi4", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"hdmi4", icon:"st.Electronics.electronics6", label: "HDMI 4", backgroundColor:"#ffffff"
    }
    standardTile("hdmi5", "device.hdmi5", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"hdmi5", icon:"st.Electronics.electronics6", label: "HDMI 5", backgroundColor:"#ffffff"
    }

    standardTile("refresh", "device.status", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
      state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh", backgroundColor:"#ffffff"
    }

    standardTile("play", "device.play", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"play", icon:"st.sonos.play-btn", backgroundColor:"#ffffff"
    }
    standardTile("pause", "device.pause", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"pause", icon:"st.sonos.pause-btn", backgroundColor:"#ffffff"
    }
    standardTile("stop", "device.stop", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
      state "default", action:"stop", icon:"st.sonos.stop-btn", backgroundColor:"#ffffff"
    }

    // Defines which tile to show in the overview
    main "state"

    // Defines which tile(s) to show when user opens the detailed view
    details([
      "state",
      "hdmi1","hdmi2","hdmi3","hdmi4","hdmi5",
      "refresh",
      "play","pause","stop"
    ])
  }
}

/**************************************************************************
 * The following section simply maps the actions as defined in
 * the metadata into onAction() calls.
 *
 * This is preferred since some actions can be dealt with more
 * efficiently this way. Also keeps all user interaction code in
 * one place.
 *
 */


def on() {
 log.debug "on()"
 sendCommand("/power/on")
}

def off() {
 log.debug "off()"
 sendCommand("/power/off")
}

def refresh() {
  log.debug "refresh()"
}

def hdmi1() {
  log.debug "hdmi1()"
  sendCommand("/source/hdmi1")
}

def hdmi2() {
  log.debug "hdmi2()"
  sendCommand("/source/hdmi2")
}

def hdmi3() {
  log.debug "hdmi3()"
  sendCommand("/source/hdmi3")
}

def hdmi4() {
  log.debug "hdmi4()"
  sendCommand("/source/hdmi4")
}

def hdmi5() {
  log.debug "hdmi5()"
  sendCommand("/source/hdmi5")
}

def play() {
  log.debug "play()"
  sendCommand("/media/play")
}

def pause() {
  log.debug "play()"
  sendCommand("/media/pause")
}

def stop() {
  log.debug "stop()"
  sendCommand("/media/stop")
}

/**************************************************************************/

/**
 * Called every so often (every 5 minutes actually) to refresh the
 * tiles so the user gets the correct information.
 */
def poll() {
  refresh()
}

def update(evt) {
  if (evt.containsKey("command")) {
    //toggle the switch on and off
    // sendEvent(name: "switch", value: (device.switch == "on") ? "off" : "on")
    // sendCommand("/button1/${(device.switch == "on") ? "off" : "on"}")

    //log.debug "setting command to ${evt.command}"
    // sendEvent(name: "command", value: "Received: ${evt.command}")
  }
}

private sendCommand(part) {
  parent.sendCommand("/plugins/generic${part}")
}
