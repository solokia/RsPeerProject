package AutoAgility.Constants;

import AutoAgility.model.SubCourse;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public enum SubCourses {
    VARROCK0(new SubCourse(Area.surrounding(new Position(3222, 3414, 0),1,0),
            new Position(3221,3414,0),"Rough wall")),
    VARROCK1(new SubCourse(Area.rectangular(3214, 3419, 3219, 3410, 3),
            new Position(3214,3414,3),"Clothes line")),
    VARROCK2(new SubCourse(Area.rectangular(3201, 3419, 3209, 3413, 3),
            new Position(3201,3416,3),"Gap")),
    VARROCK3(new SubCourse(Area.rectangular(3192, 3416, 3198, 3416, 1),
            new Position(3194,3416,1),"Wall")),
    VARROCK4(new SubCourse(Area.rectangular(3192, 3406, 3198, 3402, 3),
            new Position(3193,3402,3),"Gap")),
    VARROCK5(new SubCourse(Area.polygonal(
            new Position[] {
                    new Position(3209, 3404, 3),
                    new Position(3201, 3404, 3),
                    new Position(3201, 3399, 3),
                    new Position(3182, 3399, 3),
                    new Position(3182, 3382, 3),
                    new Position(3190, 3382, 3),
                    new Position(3190, 3387, 3),
                    new Position(3196, 3387, 3),
                    new Position(3196, 3389, 3),
                    new Position(3201, 3394, 3),
                    new Position(3209, 3394, 3)
            }),
            new Position(3208,3397,3),"Gap")),
    VARROCK6(new SubCourse(Area.polygonal(
            new Position[] {
                    new Position(3217, 3403, 3),
                    new Position(3220, 3405, 3),
                    new Position(3221, 3405, 3),
                    new Position(3222, 3403, 3),
                    new Position(3233, 3403, 3),
                    new Position(3233, 3392, 3),
                    new Position(3217, 3392, 3)
            }),
            new Position(3232,3402,3),"Gap")),
    VARROCK7(new SubCourse(Area.rectangular(3236, 3409, 3240, 3403, 3),
            new Position(3236,3408,3),"Ledge")),
    VARROCK8(new SubCourse(Area.rectangular(3236, 3410, 3240, 3416, 3),
    new Position(3237,3415,3),"Edge")),
    CANIFIS0(new SubCourse(Area.surrounding(new Position(3508, 3488, 0),1,0),
            new Position(3508,3488,0),"Tall tree")),
    CANIFIS1(new SubCourse(Area.rectangular(3503, 3499, 3511, 3490, 2),
            new Position(3506,3497,2),"Gap")),
    CANIFIS2(new SubCourse(Area.rectangular(3495, 3507, 3504, 3503, 2),
            new Position(3497,3505,2),"Gap")),
    CANIFIS3(new SubCourse(Area.rectangular(3493, 3505, 3484, 3498, 2),
            new Position(3487,3499,2),"Gap")),
    CANIFIS4(new SubCourse(Area.rectangular(3473, 3500, 3480, 3490, 3),
            new Position(3477,3492,3),"Gap")),
    CANIFIS5(new SubCourse(Area.rectangular(3476, 3488, 3484, 3480, 2),
            new Position(3480,3484,2),"Pole-vault")),
    CANIFIS6(new SubCourse(Area.rectangular(3486, 3479, 3504, 3467, 3),
            new Position(3503,3475,3),"Gap")),
    CANIFIS7(new SubCourse(Area.rectangular(3508, 3483, 3516, 3474, 2),
            new Position(3510,3482,2),"Gap"));

    private final SubCourse subCourse;

    SubCourses(SubCourse subCourse) {
        this.subCourse = subCourse;
    }

    public SubCourse getSubCourse() {
        return subCourse;
    }

}
