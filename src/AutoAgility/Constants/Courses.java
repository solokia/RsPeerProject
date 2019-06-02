package AutoAgility.Constants;

import AutoAgility.model.SubCourse;

public enum Courses {
    Draynor(10,new SubCourses[]{}),
    Varrock(30,new SubCourses[]{SubCourses.VARROCK0,SubCourses.VARROCK1,SubCourses.VARROCK2,SubCourses.VARROCK3,
            SubCourses.VARROCK4,SubCourses.VARROCK5,SubCourses.VARROCK6,SubCourses.VARROCK7,SubCourses.VARROCK8}),
    Canifis(40,new SubCourses[]{SubCourses.CANIFIS0,SubCourses.CANIFIS1,SubCourses.CANIFIS2,SubCourses.CANIFIS3,
            SubCourses.CANIFIS4,SubCourses.CANIFIS5,SubCourses.CANIFIS6,SubCourses.CANIFIS7});
    private final int level;
    private SubCourses[] subCourses;

    Courses(int level, SubCourses[] subCourses) {
        this.level = level;
        this.subCourses = subCourses;
    }

    public int getLevel() {
        return level;
    }

    public SubCourses[] getSubCourses() {
        return subCourses;
    }
}
