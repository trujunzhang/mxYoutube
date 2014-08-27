package com.mxtube.app.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class YoutubeListAdapter extends BaseAdapter {

    List<Person> persons;

    @Bean(InMemoryPersonFinder.class)
    PersonFinder personFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        persons = personFinder.findAll();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        YoutubeItemView personItemView;
        if (convertView == null) {
            personItemView = YoutubeItemView_.build(context);
        } else {
            personItemView = (personItemView) convertView;
        }

//        personItemView.bind(getItem(position));

        return personItemView;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Person getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}