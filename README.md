# Simple Man Hunt
This plugin automates creates new worlds for man hunt games. It also provides
tracking compasses to hunters that automatically update to the runner's location.

## Usage
Use `/manhunt [runner1] [runner2]...` to start a game. The server will
freeze for a bit while the worlds are created, then all players in the server
will be teleported to the new world, their inventories will be cleared
and relevant stats reset. All players not specified as a runner will be given
a compass which they can use to track the runners.

This plugin provides no winning/losing, it just manages the compasses and handles
nether and end portals working correctly with the world. However, restarting the
server/plugin will cause the world to be deleted, so don't restart.

## Contributing
Feel free to submit issues or pull requests. If you would like to help out
financially then please consider sponsoring me on GitHub, you can make a one-off
or monthly donation.
