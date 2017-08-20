/**
 *  Generic Plugin
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
var express = require('express');
var app = express();
var nconf = require('nconf');
var exec = require('child_process').exec;
var notify;
var logger = function(str) {
  mod = 'gnrc';
  console.log("[%s] [%s] %s", new Date().toISOString(), mod, str);
}
function cmd_log(error, stdout, stderr) {logger(stdout)}

/**
 * Routes
 */
app.get('/', function (req, res) {
  res.status(200).json({ status: 'Generic plugin running' });
});

app.get('/power/:cmd', function (req, res) {
  plugin.power(req.params.cmd);
  res.end();
});

app.get('/source/:input', function (req, res) {
  plugin.source(req.params.input);
  res.end();
});

app.get('/media/:cmd', function (req, res) {
  plugin.media(req.params.cmd);
  res.end();
});

module.exports = function(f) {
  notify = f;
  return app;
};

/**
 * Plugin
 */
var plugin = new Plugin();
plugin.init();

function Plugin() {
  /**
   * init (REQUIRED)
   */
  this.init = function() {
    logger('Doing something interesting during init...');
    return;
  };

  // Power
  this.power = function(cmd) {
    // Send command back to SmartThings Hub
    var data = {type: 'command', deviceId: '1', command: cmd};
    notify(JSON.stringify(data));
    logger(JSON.stringify(data));

    if (cmd == 'on') {
      exec('echo "on 0" | cec-client -s', cmd_log);
    } else if (cmd == 'off') {
      exec('echo "standby 0" | cec-client -s', cmd_log);
    }
  };

  // Source Input
  this.source = function(input) {
    // Send command back to SmartThings Hub
    // var data = {type: 'command', deviceId: '1', command: input};
    // notify(JSON.stringify(data));
    // logger(JSON.stringify(data));

    address = input.replace("hdmi", "") + "0:00"
    logger("echo 'tx 1F:82:" + address + "' | cec-client -s");
    exec("echo 'tx 1F:82:" + address + "' | cec-client -s", cmd_log);
  };

  // Media
  this.media = function(cmd) {
    // Send command back to SmartThings Hub
    // var data = {type: 'command', deviceId: '1', command: cmd};
    // notify(JSON.stringify(data));
    // logger(JSON.stringify(data));

    if (cmd == 'play') {
      exec('echo "tx 14:41:24" | cec-client -s', cmd_log);
    } else if (cmd == 'pause') {
      exec('echo "tx 14:41:25" | cec-client -s', cmd_log);
    } else if (cmd == 'stop') {
      exec('echo "tx 14:42:03" | cec-client -s', cmd_log);
    }
  };
}
