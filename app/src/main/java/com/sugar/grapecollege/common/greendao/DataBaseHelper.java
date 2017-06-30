package com.sugar.grapecollege.common.greendao;

import android.content.Context;

import com.sugar.grapecollege.common.greendao.model.DaoMaster;
import com.sugar.grapecollege.common.greendao.model.DaoSession;
import com.sugar.grapecollege.common.greendao.model.GreenDaoConstants;
import com.sugar.grapecollege.common.greendao.model.ModelProduct;
import com.sugar.grapecollege.common.greendao.model.ModelProductDao;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/4/25 14:06
 * @Description 数据库操作帮助类
 */

public class DataBaseHelper {

    private static DataBaseHelper helper = new DataBaseHelper();
    private DaoSession daoSession;

    public static DataBaseHelper getInstance() {
        return helper;
    }

    /**
     * 初始化数据库
     */
    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, GreenDaoConstants.dataBaseName);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public ModelProductDao getModelProductDao() {
        return daoSession.getModelProductDao();
    }


    public void insert(ModelProduct... modelProducts) {
        if (modelProducts != null && modelProducts.length > 0) {
            insert(Arrays.asList(modelProducts));
        }
    }

    public void insert(List<ModelProduct> list) {
        if (list != null) {
            for (ModelProduct model : list) {
                insert(model);
            }
        }
    }

    public void insert(ModelProduct modelProduct) {
        daoSession.getModelProductDao().insert(modelProduct);
    }

    public void insertOrReplace(ModelProduct... modelProducts) {
        if (modelProducts != null && modelProducts.length > 0) {
            insertOrReplace(Arrays.asList(modelProducts));
        }
    }

    public void insertOrReplace(List<ModelProduct> list) {
        if (list != null) {
            for (ModelProduct model : list) {
                insertOrReplace(model);
            }
        }
    }

    public void insertOrReplace(ModelProduct modelProduct) {
        daoSession.getModelProductDao().insertOrReplace(modelProduct);
    }

    public void delete(ModelProduct modelProduct) {
        daoSession.getModelProductDao().delete(modelProduct);
    }

    public void delete(ModelProduct... arr) {
        if (arr != null) {
            daoSession.getModelProductDao().deleteInTx(arr);
        }
    }

    public void delete(List<ModelProduct> list) {
        if (list != null) {
            daoSession.getModelProductDao().deleteInTx(list);
        }
    }

    public void delete(String fontId) {
        delete(daoSession.getModelProductDao().queryBuilder().where(ModelProductDao.Properties.ProductId.eq(fontId)).unique());
    }

    public void deleteAll() {
        daoSession.getModelProductDao().deleteAll();
    }

    public void update(ModelProduct modelProduct) {
        daoSession.getModelProductDao().update(modelProduct);
    }

    public void update(ModelProduct... modelProducts) {
        daoSession.getModelProductDao().updateInTx(modelProducts);
    }

    public void update(Iterable<ModelProduct> iterable) {
        daoSession.getModelProductDao().updateInTx(iterable);
    }

    public List<ModelProduct> queryAll() {
        return daoSession.getModelProductDao().loadAll();
    }

    public ModelProduct query(String fontId) {
        return daoSession.getModelProductDao().queryBuilder().where(ModelProductDao.Properties.ProductId.eq(fontId)).unique();
    }

    /**
     * 根据特定属性的值查询表
     * 用法例：
     * DataBaseHelper.getInstance().queryBy(ModelFontDao.Properties.FontId, "1234567")
     *
     * @param property 属性，类似{@link com.sugar.grapecollege.common.greendao.model.ModelProductDao.Properties#ProductName }等等...
     * @param value    属性值
     */
    public List<ModelProduct> queryBy(Property property, Object value) {
        return daoSession.getModelProductDao().queryBuilder().where(property.eq(value)).list();
    }

    /**
     * 根据多个属性的值查询表
     * 用法例：
     * HashMap<Property, Object> map = new HashMap<>();
     * map.put(ModelFontDao.Properties.FontId,"12345678");
     * DataBaseHelper.getInstance().queryBy(map);
     *
     * @param params 属性和属性值Map
     *               key:类似{@link com.sugar.grapecollege.common.greendao.model.ModelProductDao.Properties#ProductName }等等...
     *               vale:属性值
     */
    public List<ModelProduct> queryBy(Map<Property, Object> params) {
        if (params != null && !params.isEmpty()) {
            QueryBuilder<ModelProduct> builder = daoSession.getModelProductDao().queryBuilder();
            Set<Property> set = params.keySet();
            for (Property property : set) {
                Object value = params.get(property);
                builder.where(property.eq(value));
            }
            return builder.list();
        }
        return new ArrayList<>(0);
    }
}
