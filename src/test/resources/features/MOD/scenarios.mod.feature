#lang: ko
Feature: 리뷰 수정 이벤트 처리 [REVIEW, MOD]

  Background: 리뷰 수정 이벤트 처리를 위해서는 특정 장소와 유저, 수정할 리뷰글이 존재해야 함

    Given MOD_1_아래와 같이 특정 장소가 등록되어 있음
      | placeId                              | country | name | bonusPoint |
      | cd1aa206-5768-4cdd-b054-661b6b35d687 | 호주     | 시드니  | 1          |

    And MOD_2_아래와 같이 특정 유저가 등록되어 있음
      | userId                               | name     | rewardPoint |
      | b87d09e5-e97e-4588-badb-b5599ef95e40 | Jenny    | 1           |

    And MOD_3_유저가 아래와 같이 특정 장소에 대해 리뷰를 작성하였음
      | reviewId                              | placeId                              | content       | attachedPhotoIds | userId                               | rewarded |
      | ebb3e526-fd9e-448c-b4f7-665b814150a6  | cd1aa206-5768-4cdd-b054-661b6b35d687 | 시드니도 좋아요    |                  | b87d09e5-e97e-4588-badb-b5599ef95e40 | 1        |

    And MOD_4_리뷰 작성에 대한 보상으로 아래와 같이 유저에게 포인트가 부여되었음
      | rewardId                              | userId                                | reviewId                             | operation |  pointDelta | reason |
      | 81c20067-e377-41a8-ae77-3f1cd4689beb  | b87d09e5-e97e-4588-badb-b5599ef95e40  | ebb3e526-fd9e-448c-b4f7-665b814150a6 | ADD       |  1          | NEW    |

#  Rule: 유저가 수정한 글이 리뷰 리워드 대상일 경우, 리뷰 포인트에 증감이 있는 경우에 한해 리워드 포인트를 조정함

    Scenario: 사용자가 기존에 작성하였던 리뷰를 수정함

      Given MOD_5_유저의 현재 포인트 총점은 아래와 같음
        | userId                                | totalPoint |
        | b87d09e5-e97e-4588-badb-b5599ef95e40  | 1          |

      When MOD_6_유저가 아래와 같이 작성했던 리뷰를 수정함
        | type   | action | reviewId                              | content | attachedPhotoIds                      | userId                               | placeId                              |
        | REVIEW | MOD    | 240a0658-dc5f-4878-9831-ebb7b26687772 | 좋아요    | 49111fdf-ee89-4ee1-9086-24e4a4f36967  | 3ede0ef2-92b7-4817-a5f3-0c575361f745 |  2e4baf1c-5acb-4efb-a1af-eddada31b00f|

      Then MOD_7_유저의 리워드 레코드가 아래와 같이 변경됨
        | userId                               | reviewId                              | operation | pointDelta | reason |
        | 3ede0ef2-92b7-4817-a5f3-0c575361f745 | 240a0658-dc5f-4878-9831-ebb7b26687772 | SUB       | 3          | MOD    |
        | 3ede0ef2-92b7-4817-a5f3-0c575361f745 | 240a0658-dc5f-4878-9831-ebb7b26687772 | ADD       | 3          | MOD    |
        | 3ede0ef2-92b7-4817-a5f3-0c575361f745 | 240a0658-dc5f-4878-9831-ebb7b26687772 | ADD       | 2          | MOD    |

      And MOD_8_유저의 포인트 총점이 아래와 같아짐
          | userId                                | totalPoint |
          | 3ede0ef2-92b7-4817-a5f3-0c575361f745  | 2          |

      And MOD_9_유저의 리뷰 레코드가 아래와 같이 변경됨
          | reviewId                               | placeId                               | content | attachedPhotoIds | userId                                | rewarded |
          | 240a0658-dc5f-4878-9831-ebb7b26687772  | 2e4baf1c-5acb-4efb-a1af-eddada31b00f  | 좋아요    |                  | 3ede0ef2-92b7-4817-a5f3-0c575361f745  | 1        |

