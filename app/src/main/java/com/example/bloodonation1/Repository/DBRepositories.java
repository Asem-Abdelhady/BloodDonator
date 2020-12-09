package com.example.bloodonation1.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bloodonation1.Interfaces.RequestDAO;
import com.example.bloodonation1.Models.LocalRequestModel;
import com.example.bloodonation1.RoomDB.RequestDatabase;

import java.util.List;

public class DBRepositories {
    private RequestDAO requestDAO;
    private LiveData<List<LocalRequestModel>> allRequests;

    public DBRepositories(Application application) {
        RequestDatabase database = RequestDatabase.getInstance(application);
        requestDAO = database.requestDAO();
        allRequests = requestDAO.getAllRequestes();
    }

    public void insert(LocalRequestModel model) {
        new InsertRequestAsyncTask(requestDAO).execute(model);
    }

    public void update(LocalRequestModel model) {
        new UpdateRequestAsyncTask(requestDAO).execute(model);
    }

    public void delete(LocalRequestModel model) {
        new DeleteRequestAsyncTask(requestDAO).execute(model);
    }

    public void deleteAllRequestes() {
        new DeleteAllRequestesAsyncTask(requestDAO).execute();
    }

    public LiveData<List<LocalRequestModel>> getAllRequestes() {
        return allRequests;
    }

    private static class InsertRequestAsyncTask extends AsyncTask<LocalRequestModel, Void, Void> {
        private RequestDAO requestDAO;

        private InsertRequestAsyncTask(RequestDAO requestDAO) {
            this.requestDAO = requestDAO;
        }

        @Override
        protected Void doInBackground(LocalRequestModel... models) {
            requestDAO.insert(models[0]);
            return null;
        }
    }

    private static class UpdateRequestAsyncTask extends AsyncTask<LocalRequestModel, Void, Void> {
        private RequestDAO requestDAO;

        private UpdateRequestAsyncTask(RequestDAO requestDAO) {
            this.requestDAO = requestDAO;
        }

        @Override
        protected Void doInBackground(LocalRequestModel... models) {
            requestDAO.update(models[0]);
            return null;
        }
    }

    private static class DeleteRequestAsyncTask extends AsyncTask<LocalRequestModel, Void, Void> {
        private RequestDAO requestDAO;

        private DeleteRequestAsyncTask(RequestDAO requestDAO) {
            this.requestDAO = requestDAO;
        }

        @Override
        protected Void doInBackground(LocalRequestModel... models) {
            requestDAO.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllRequestesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RequestDAO requestDAO;

        private DeleteAllRequestesAsyncTask(RequestDAO requestDAO) {
            this.requestDAO = requestDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            requestDAO.deleteAllRequestes();
            return null;
        }
    }
}