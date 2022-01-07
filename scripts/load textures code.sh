#!/usr/bin/env bash

ls ../raw/png | sed -r 's/(.*)\.png/\tval \1 = loadTextureRegion\("\1"\)/g' | xclip -selection clipboard
echo "Code copied to clipboard."
