package com.techelevator;

// Vending Machine Command Line Interface application
public class VendingMachineCLI_RT {

	public static void main(String[] args) {

		// Ryan wrote all classes ending in the initials RT. Joseph wrote all the classes not appended with initials.
		// We had different ideas about the design, so we both wrote a version with help from the other.

		VendingMachine_RT vm = new VendingMachine_RT();
		UserInteraction_RT ui = new UserInteraction_RT(vm);

		System.out.println("VENDO-MATIC 800\n");

		ui.mainMenu();
	}
}