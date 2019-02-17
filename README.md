# file-parser

Parses input files and prints output on startup; then spawns Jetty server.

## Usage

    $ lein run

## Options

    -c --comma-file FILE Parse comma-seperated file
    -s --space-file FILE Parse space-seperated file
    -p --pipe-file FILE Parse pipe-seperated file
    -P --port PORT Webserver port (default 3000)

## Examples

    $ lein run -c resources/comma.txt -p resources/pipe.txt -s resources/space.txt

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
