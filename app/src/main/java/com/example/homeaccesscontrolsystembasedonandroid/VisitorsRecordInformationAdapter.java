package com.example.homeaccesscontrolsystembasedonandroid;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VisitorsRecordInformationAdapter
        extends RecyclerView.Adapter<VisitorsRecordInformationAdapter.VisitorsRecordInformationViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public VisitorsRecordInformationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public VisitorsRecordInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.visitor_record_information_layout, parent, false);
        return new VisitorsRecordInformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder( VisitorsRecordInformationViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry._ID);
        int imageIndex = mCursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_VISITOR_IMAGE);
        int remarkIndex = mCursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_REMARK_INFO);
        int timeIndex = mCursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME);
        int resultIndex = mCursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT);

        mCursor.moveToPosition(position);

        final int id = mCursor.getInt(idIndex);
        Bitmap personBitmap = cursorToBmp(mCursor, imageIndex);
        String remarkString = mCursor.getString(remarkIndex);
        String timeString = mCursor.getString(timeIndex);
        String resultString = mCursor.getString(resultIndex);

        if (resultString.equals(VisitorsRecordContract.VisitorsRecordEntry.AUTHORIZED_RESULT)) {
            holder.applicationResultTextView.setTextColor(Color.rgb(0,255,0));
        }
        else if (resultString.equals(VisitorsRecordContract.VisitorsRecordEntry.DENIED_RESULT)) {
            holder.applicationResultTextView.setTextColor(Color.rgb(255,0,0));
        }
        else if (resultString.equals(VisitorsRecordContract.VisitorsRecordEntry.DEFAULT_RESULT)) {
            holder.applicationResultTextView.setTextColor(Color.rgb(255,255,0));
        }

        holder.itemView.setTag(id);
        holder.personImageView.setImageBitmap(personBitmap);
        holder.remarkTextView.setText(remarkString);
        holder.timeTextView.setText(timeString);
        holder.applicationResultTextView.setText(resultString);
    }

    //Cursor to bitmap
    Bitmap cursorToBmp(Cursor c, int columnIndex) {
        byte[] data = c.getBlob(columnIndex);
        try {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    class VisitorsRecordInformationViewHolder extends RecyclerView.ViewHolder {

        TextView applicationResultTextView;
        ImageView personImageView;
        TextView remarkTextView;
        TextView timeTextView;

        public VisitorsRecordInformationViewHolder(View itemView) {
            super(itemView);

            applicationResultTextView = itemView.findViewById(R.id.visitor_records_layout_result);
            personImageView = itemView.findViewById(R.id.visitor_records_layout_image);
            remarkTextView = itemView.findViewById(R.id.visitor_records_layout_remark);
            timeTextView = itemView.findViewById(R.id.visitor_records_layout_time);
        }
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
}
