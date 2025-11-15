#!/bin/bash

echo "================================================"
echo " Running The Sweet Cupcake Shop System (GUI)"
echo "================================================"
echo ""

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo "Error: bin directory not found!"
    echo "Please compile the project first using ./compile.sh"
    exit 1
fi

# Run the GUI application
java -cp bin view.gui.CupcakeShopGUI
