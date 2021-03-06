package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;

import static chapter4.Dish.dishTags;
import static chapter4.Dish.menu;
import static java.util.stream.Collectors.*;

public class Grouping {

    public void grouping() {
        Map<Dish.Type, List<Dish>> dishesType = menu.stream()
                .collect(groupingBy(Dish::getType));
    }

    enum CaloricLevel { Diet, Normal, Fat }

    public static void customGrouping() {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() < 400) return CaloricLevel.Diet;
                    else if (dish.getCalories() < 700) return CaloricLevel.Normal;
                    else return CaloricLevel.Fat;
                }));
        System.out.println("dishesByCaloricLevel = " + dishesByCaloricLevel);
    }

    public static void groupingByCalorieBiggerThen500() {
//        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
//                .collect(Collectors.groupingBy(Dish::getType, Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));
//        System.out.println(dishesByType);
    }

    public static void groupingByTypeMappingName() {
        Map<Dish.Type, List<String>> dishes = menu.stream()
                .collect(groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        System.out.println(dishes);
    }

//    public static void makeFlatMapping() {
//        Map<Dish.Type, Set<String>> map = menu.stream()
//                .collect(groupingBy(
//                        Dish::getType,
//                        flatMapping(d -> dishTags.get(d.getName()).stream(),
//                                toSet())));
//    }
    public static void multipleGrouping() {
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.Diet;
                            else if (dish.getCalories() <= 700) return CaloricLevel.Normal;
                            else return CaloricLevel.Fat;
                        })));

        System.out.println(collect);
    }

    public static void highestByMultipleGrouping() {
        Map<Dish.Type, Dish> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)),
                            Optional::get)));

        System.out.println(collect);
    }

    // ???????????? ????????? ?????? ????????? ???????????? ?????? groupingBy??? ??? ?????? ????????? ????????? ????????? ??????
    // ??? ????????? ????????? ?????? ???????????? ??????
    public static void totalCaloriesByType() {
        Map<Dish.Type, Integer> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        reducing(0, Dish::getCalories, Integer::sum)));
        System.out.println("collect = " + collect);

        Map<Dish.Type, Integer> collect1 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        summingInt(Dish::getCalories)));
        System.out.println("collect1 = " + collect1);
    }

    // mapping ???????????? ???????????? ????????? ???????????? ??????, ?????? ????????? ???????????? ???????????? ????????? ?????????.
    // ???????????? ?????? ?????? ????????? ???????????? ???????????? ?????? ??????
    // ??? ?????? ????????? ???????????? ????????? ??????
    public static void caloricLevelByType() {
        Map<Dish.Type, Set<CaloricLevel>> collect = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.Diet;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.Normal;
                                    else return CaloricLevel.Fat;
                                }
                                , toCollection(HashSet::new)
                        )));
        System.out.println("collect = " + collect);
    }

    public static void main(String[] args) {
//        customGrouping();
//        groupingByCalorieBiggerThen500();
//        groupingByTypeMappingName();
//        multipleGrouping();
//        highestByMultipleGrouping();
//        totalCaloriesByType();
        caloricLevelByType();
    }
}
