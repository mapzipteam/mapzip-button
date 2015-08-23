package com.example.ppangg.mapzipproject.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ppangg.mapzipproject.R;

import java.util.ArrayList;

public class serarch_Fragment extends Fragment implements AbsListView.OnScrollListener{


    private View v;

    private ArrayList<MyItem> marItem;
    private MyListAdapter 	  mMyAdapte;
    private ListView mListView;
    private MyItem 			  items;

    //
    //
    // ��ũ�� �ε�
    private LayoutInflater mInflater;
    private boolean mLockListView;

    public serarch_Fragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

       v = inflater.inflate(R.layout.fragment_search, container, false);

        mListView = (ListView)v.findViewById(R.id.searchList);
        marItem = new ArrayList<MyItem>();

        mLockListView = true;

        // Ǫ�͸� ���. setAdapter ������ �ؾ���.
        mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListView.addFooterView(mInflater.inflate(R.layout.listview_footer, null));

        // ��ũ�� ������ ���
        mListView.setOnScrollListener(this);

        mMyAdapte = new MyListAdapter(getActivity(), R.layout.custom_listview, marItem);
        mListView.setAdapter(mMyAdapte);

        // �ӽ� ������ ���
        addItems(10);
         
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }
    // ����Ʈ�� ��� �׸�
    class MyItem
    {
        MyItem(String _coustId)
        {
            sCustId = _coustId;
        }
        String sCustId;
    }

    // ����� Ŭ����
    class MyListAdapter extends BaseAdapter
    {
        Context cContext;
        LayoutInflater lInflater;
        ArrayList<MyItem> alSrc;
        int layout;

        public MyListAdapter(Context _context, int _layout, ArrayList<MyItem> _arrayList)
        {
            cContext  = _context;
            lInflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            alSrc	  = _arrayList;
            layout    = _layout;
        }

        @Override
        public int getCount()
        {
            return alSrc.size();
        }

        @Override
        public Object getItem(int position)
        {
            return alSrc.get(position).sCustId;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        // �� ���� �׸� ����
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final int pos = position;
            if(convertView == null)
            {
                convertView = lInflater.inflate(layout, parent, false);
            }

            final String getCustId = alSrc.get(pos).sCustId;

            TextView tvCustId = (TextView)convertView.findViewById(R.id.tvCoustId);
            tvCustId.setText(alSrc.get(position).sCustId);

            Button btSending = (Button)convertView.findViewById(R.id.listBtn);
            btSending.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });

            return convertView;
        }
    }

    // ���� ������ �߰�
    private void addItems(final int size)
    {
        // �������� �߰��ϴ� ���� �ߺ� ��û�� �����ϱ� ���� ���� �ɾ�Ӵϴ�.
        mLockListView = true;
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                for(int i = 0 ; i < size ; i++)
                {
                    items = new MyItem("more " + i);
                    marItem.add(items);
                }
                // ��� �����͸� �ε��Ͽ� �����Ͽ��ٸ� ����Ϳ� �˸���
                // ����Ʈ���� ���� �����մϴ�.
                mMyAdapte.notifyDataSetChanged();
                mLockListView = false;
            }
        };
        // �ӵ��� �����̸� �����ϱ� ���� �ļ�
        Handler handler = new Handler();
        handler.postDelayed(run, 3000);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // ���� ���� ó���� ���̴� ����ȣ�� �������� ����ȣ�� ���Ѱ���
        // ��ü�� ���ڿ� ���������� ���� �Ʒ��� ��ũ�� �Ǿ��ٰ� �����մϴ�.
        int count = totalItemCount - visibleItemCount;

        if(firstVisibleItem >= count && totalItemCount != 0 && mLockListView == false)
        {
            Log.i("list", "Loading next items");
            addItems(10);
        }

    }
}
