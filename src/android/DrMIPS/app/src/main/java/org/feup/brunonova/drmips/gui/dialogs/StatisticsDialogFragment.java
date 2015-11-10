/*
    DrMIPS - Educational MIPS simulator
    Copyright (C) 2013-2015 Bruno Nova <brunomb.nova@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.feup.brunonova.drmips.gui.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.feup.brunonova.drmips.R;
import org.feup.brunonova.drmips.gui.DrMIPSActivity;
import org.feup.brunonova.drmips.simulator.mips.CPU;

public class StatisticsDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
	@Override
	@SuppressLint("InflateParams")
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);

		View layout = getActivity().getLayoutInflater().inflate(R.layout.statistics_dialog, null);
		AlertDialog dialog =  new AlertDialog.Builder(getActivity())
			.setTitle(R.string.statistics)
			.setView(layout)
			.setPositiveButton(android.R.string.ok, this)
			.create();

		final CPU cpu = ((DrMIPSActivity)getActivity()).getCPU();
		((TextView)layout.findViewById(R.id.lblClockPeriodVal)).setText(cpu.getClockPeriod() + " " + CPU.LATENCY_UNIT);
		((TextView)layout.findViewById(R.id.lblClockFrequencyVal)).setText(cpu.getClockFrequencyInAdequateUnit());
		((TextView)layout.findViewById(R.id.lblExecutedCyclesVal)).setText(cpu.getNumberOfExecutedCycles() + "");
		((TextView)layout.findViewById(R.id.lblExecutionTimeVal)).setText(cpu.getExecutionTime() + " " + CPU.LATENCY_UNIT);
		((TextView)layout.findViewById(R.id.lblExecutedInstructionsVal)).setText(cpu.getNumberOfExecutedInstructions() + "");
		((TextView)layout.findViewById(R.id.lblCPIVal)).setText(cpu.getCPIAsString());
		((TextView)layout.findViewById(R.id.lblForwardsVal)).setText(cpu.getNumberOfForwards() + "");
		((TextView)layout.findViewById(R.id.lblStallsVal)).setText(cpu.getNumberOfStalls() + "");

		return dialog;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		dismiss();
	}
}
