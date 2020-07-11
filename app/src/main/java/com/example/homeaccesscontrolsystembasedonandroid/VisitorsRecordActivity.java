package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class VisitorsRecordActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = VisitorsRecordActivity.class.getSimpleName();
    private static final int VISITORS_RECORD_LOADER_ID = 27;

    private VisitorsRecordDBHelper mVisitorsRecordDBHelper = new VisitorsRecordDBHelper(this);

    RecyclerView mRecyclerView;
    private VisitorsRecordInformationAdapter mVisitorsRecordInformationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_record);

        mRecyclerView = findViewById(R.id.visitors_records_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVisitorsRecordInformationAdapter = new VisitorsRecordInformationAdapter(this);
        mRecyclerView.setAdapter(mVisitorsRecordInformationAdapter);

        getSupportLoaderManager().initLoader(VISITORS_RECORD_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(VISITORS_RECORD_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mVisitorRecord = null;

            @Override
            protected void onStartLoading() {
                if (mVisitorRecord != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mVisitorRecord);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {

//                String mSelection = ResidentContract.ResidentEntry.COLUMN_HOUSE_NUMBER +
//                        " = ? and " + ResidentContract.ResidentEntry.COLUMN_RESIDENT_NAME +
//                        " = ?";
//                String[] mSelectionArgs = {identityInformation[0], identityInformation[1]};

                try {
                    mVisitorRecord = mVisitorsRecordDBHelper.getReadableDatabase().query(
                            VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME,
                            null, null, null,null,null,
                            VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME + " desc");
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }

                return mVisitorRecord;
            }

            public void deliverResult(Cursor data) {
                mVisitorRecord = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        mVisitorsRecordInformationAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mVisitorsRecordInformationAdapter.swapCursor(null);
    }
}
