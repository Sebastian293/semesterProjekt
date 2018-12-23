package srauhbaasch.chucks;

public class Category {
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImageId() throws NoSuchFieldException, IllegalAccessException {
        return R.drawable.class.getField(categoryName).getInt(null);
    }

    public int getStringId() throws NoSuchFieldException, IllegalAccessException {
        return R.string.class.getField(categoryName).getInt(null);
    }

    public  String getCategoryName(){
        return categoryName;
    }
}
