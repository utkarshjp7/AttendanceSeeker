package cis470.attendanceseeker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import cis470.attendanceseeker.entities.StudentAttendance;

public class AttendanceRecyclerAdapter extends RecyclerView.Adapter<AttendanceRecyclerAdapter.AttendanceViewHolder> {

    private List<StudentAttendance> mAttendanceList;

    public AttendanceRecyclerAdapter(List<StudentAttendance> attendanceList) {
        this.mAttendanceList = attendanceList;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public TextView studentId, studentName, isPresent;

        public AttendanceViewHolder(View view) {
            super(view);
            studentId = view.findViewById(R.id.attendance_list_id);
            studentName = view.findViewById(R.id.attendance_list_name);
            isPresent = view.findViewById(R.id.attendance_list_present);
        }
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_list_row, parent, false);

        return new AttendanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        StudentAttendance attendance = mAttendanceList.get(position);
        holder.studentId.setText(attendance.getStudentId());
        holder.studentName.setText(attendance.getStudentName());
        String isPresent = attendance.isPresent() ? "P" : "A";
        holder.isPresent.setText(isPresent);
    }

    @Override
    public int getItemCount() {
        return mAttendanceList.size();
    }
}
