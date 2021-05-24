/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

/**
 *
 * @author Marcin
 */
public class GLTFWriter {
    
    public String writeGLTF(String poi){
        
        /* String to write into gltf files, simplest solution, need to insert poi in three places */
        
        String output = "";
        
        output += "{\n" +
                "    \"asset\" : {\n" +
                "        \"extensions\" : {\n" +
                "            \"ASOBO_normal_map_convention\" : {\n" +
                "                \"tangent_space_convention\" : \"DirectX\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"generator\" : \"Extended Khronos glTF Blender I/O v1.0.0\",\n" +
                "        \"version\" : \"2.0\"\n" +
                "    },\n" +
                "    \"extensionsUsed\" : [\n" +
                "        \"ASOBO_normal_map_convention\",\n" +
                "        \"ASOBO_material_day_night_switch\",\n" +
                "        \"ASOBO_material_shadow_options\",\n" +
                "        \"ASOBO_macro_light\"\n" +
                "    ],\n" +
                "    \"scene\" : 0,\n" +
                "    \"scenes\" : [\n" +
                "        {\n" +
                "            \"name\" : \"Scene\",\n" +
                "            \"nodes\" : [\n" +
                "                0,\n" +
                "                1,\n" +
                "                2,\n" +
                "                3,\n" +
                "                4,\n" +
                "                5,\n" +
                "                6,\n" +
                "                7,\n" +
                "                8,\n" +
                "                9,\n" +
                "                10\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"nodes\" : [\n" +
                "        {\n" +
                "            \"mesh\" : 0,\n" +
                "            \"name\" : \"base\",\n" +
                "            \"rotation\" : [\n" +
                "                0,\n" +
                "                1,\n" +
                "                0,\n" +
                "                -4.371138828673793e-08\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                153.9088897705078,\n" +
                "                103.5848617553711,\n" +
                "                20\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                -500,\n" +
                "                0\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 1,\n" +
                "            \"name\" : \"arrow_down\",\n" +
                "            \"rotation\" : [\n" +
                "                -1,\n" +
                "                0,\n" +
                "                0,\n" +
                "                1.910685676922942e-15\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                0.800000011920929,\n" +
                "                1.9233323335647583,\n" +
                "                0.800000011920929\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                -50.851295471191406,\n" +
                "                0\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 2,\n" +
                "            \"name\" : \"billboard_1\",\n" +
                "            \"rotation\" : [\n" +
                "                0.537299633026123,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0.8433914184570312\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                3,\n" +
                "                27.519142150878906\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_a.001\",\n" +
                "            \"rotation\" : [\n" +
                "                0.7049270868301392,\n" +
                "                0.7049270868301392,\n" +
                "                -0.05547890067100525,\n" +
                "                0.0554790198802948\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -23.753936767578125,\n" +
                "                9,\n" +
                "                69.94385528564453\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_b.001\",\n" +
                "            \"rotation\" : [\n" +
                "                0.7049270868301392,\n" +
                "                0.7049270868301392,\n" +
                "                -0.05547890067100525,\n" +
                "                0.0554790198802948\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                25,\n" +
                "                9,\n" +
                "                70\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 3,\n" +
                "            \"name\" : \"billboard_2\",\n" +
                "            \"rotation\" : [\n" +
                "                0.26864978671073914,\n" +
                "                0.730398416519165,\n" +
                "                -0.4653151333332062,\n" +
                "                0.4216957092285156\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                26.869400024414062,\n" +
                "                3,\n" +
                "                -18.963499069213867\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_b.002\",\n" +
                "            \"rotation\" : [\n" +
                "                0.33711645007133484,\n" +
                "                0.37305915355682373,\n" +
                "                -0.6888810992240906,\n" +
                "                -0.5221322774887085\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                76.54889678955078,\n" +
                "                9,\n" +
                "                -16.92221450805664\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_a.002\",\n" +
                "            \"rotation\" : [\n" +
                "                0.3477298617362976,\n" +
                "                0.3694729506969452,\n" +
                "                -0.7031799554824829,\n" +
                "                -0.4981082081794739\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                50.254920959472656,\n" +
                "                9,\n" +
                "                -61.36131286621094\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 4,\n" +
                "            \"name\" : \"billboard_3\",\n" +
                "            \"rotation\" : [\n" +
                "                0.26864978671073914,\n" +
                "                -0.730398416519165,\n" +
                "                0.4653151333332062,\n" +
                "                0.4216957092285156\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -26.869400024414062,\n" +
                "                3,\n" +
                "                -18.963499069213867\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_a.003\",\n" +
                "            \"rotation\" : [\n" +
                "                0.45131075382232666,\n" +
                "                0.19041132926940918,\n" +
                "                0.7227853536605835,\n" +
                "                0.48748698830604553\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -50.25490188598633,\n" +
                "                9,\n" +
                "                -61.36131286621094\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_macro_light\" : {\n" +
                "                    \"color\" : [\n" +
                "                        1,\n" +
                "                        0.7722502946853638,\n" +
                "                        0.3688167929649353\n" +
                "                    ],\n" +
                "                    \"intensity\" : 200,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_b.003\",\n" +
                "            \"rotation\" : [\n" +
                "                0.3655388355255127,\n" +
                "                0.3397868275642395,\n" +
                "                0.534143328666687,\n" +
                "                0.6823614835739136\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -76.54889678955078,\n" +
                "                9,\n" +
                "                -16.92221450805664\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"materials\" : [\n" +
                "        {\n" +
                "            \"emissiveFactor\" : [\n" +
                "                0,\n" +
                "                0,\n" +
                "                0\n" +
                "            ],\n" +
                "            \"name\" : \"base_MSFS_mat\",\n" +
                "            \"pbrMetallicRoughness\" : {\n" +
                "                \"baseColorFactor\" : [\n" +
                "                    0.15232576429843903,\n" +
                "                    0.09750441461801529,\n" +
                "                    0.0005894238129258156,\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"metallicFactor\" : 0,\n" +
                "                \"roughnessFactor\" : 0.5\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"emissiveFactor\" : [\n" +
                "                0.800000011920929,\n" +
                "                0.7603908181190491,\n" +
                "                0.4314608573913574\n" +
                "            ],\n" +
                "            \"name\" : \"arrow_MSFS_mat\",\n" +
                "            \"pbrMetallicRoughness\" : {\n" +
                "                \"baseColorFactor\" : [\n" +
                "                    1,\n" +
                "                    1,\n" +
                "                    1,\n" +
                "                    1\n" +
                "                ],\n" +
                "                \"metallicFactor\" : 0,\n" +
                "                \"roughnessFactor\" : 0.5\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"emissiveFactor\" : [\n" +
                "                0.30000001192092896,\n" +
                "                0.30000001192092896,\n" +
                "                0.30000001192092896\n" +
                "            ],\n" +
                "            \"extensions\" : {\n" +
                "                \"ASOBO_material_day_night_switch\" : {\n" +
                "                    \"enabled\" : true\n" +
                "                },\n" +
                "                \"ASOBO_material_shadow_options\" : {\n" +
                "                    \"noCastShadow\" : true\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"billboard_MSFS_mat\",\n" +
                "            \"pbrMetallicRoughness\" : {\n" +
                "                \"baseColorTexture\" : {\n" +
                "                    \"index\" : 0,\n" +
                "                    \"texCoord\" : 0\n" +
                "                },\n" +
                "                \"metallicFactor\" : 0,\n" +
                "                \"roughnessFactor\" : 0.5\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"meshes\" : [\n" +
                "        {\n" +
                "            \"name\" : \"base\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 0,\n" +
                "                        \"NORMAL\" : 1,\n" +
                "                        \"TEXCOORD_0\" : 2\n" +
                "                    },\n" +
                "                    \"indices\" : 3,\n" +
                "                    \"material\" : 0\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"arrow_down.001\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 4,\n" +
                "                        \"NORMAL\" : 5,\n" +
                "                        \"TEXCOORD_0\" : 6\n" +
                "                    },\n" +
                "                    \"indices\" : 7,\n" +
                "                    \"material\" : 1\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_1\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 8,\n" +
                "                        \"NORMAL\" : 9,\n" +
                "                        \"TEXCOORD_0\" : 10\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 2\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_1.002\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 12,\n" +
                "                        \"NORMAL\" : 13,\n" +
                "                        \"TEXCOORD_0\" : 14\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 2\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_3.001\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 15,\n" +
                "                        \"NORMAL\" : 16,\n" +
                "                        \"TEXCOORD_0\" : 17\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 2\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"textures\" : [\n" +
                "        {\n" +
                "            \"source\" : 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"images\" : [\n" +
                "        {\n" +
                "            \"mimeType\" : \"image/png\",\n" +
                "            \"name\" : \"" + poi + "\",\n" +
                "            \"uri\" : \"" + poi + ".png\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"accessors\" : [\n" +
                "        {\n" +
                "            \"bufferView\" : 0,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 6,\n" +
                "            \"max\" : [\n" +
                "                0.8660253882408142,\n" +
                "                1,\n" +
                "                0.5000000596046448\n" +
                "            ],\n" +
                "            \"min\" : [\n" +
                "                -0.866025447845459,\n" +
                "                -1,\n" +
                "                -1\n" +
                "            ],\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 1,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 2,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 3,\n" +
                "            \"componentType\" : 5123,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"SCALAR\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 4,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 12,\n" +
                "            \"max\" : [\n" +
                "                0.8660253882408142,\n" +
                "                75,\n" +
                "                0.5000000596046448\n" +
                "            ],\n" +
                "            \"min\" : [\n" +
                "                -0.866025447845459,\n" +
                "                -25,\n" +
                "                -1\n" +
                "            ],\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 5,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 6,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 7,\n" +
                "            \"componentType\" : 5123,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"SCALAR\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 8,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"max\" : [\n" +
                "                50,\n" +
                "                0,\n" +
                "                50\n" +
                "            ],\n" +
                "            \"min\" : [\n" +
                "                -50,\n" +
                "                0,\n" +
                "                -50\n" +
                "            ],\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 9,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 10,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 11,\n" +
                "            \"componentType\" : 5123,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"SCALAR\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 12,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"max\" : [\n" +
                "                50,\n" +
                "                0,\n" +
                "                50\n" +
                "            ],\n" +
                "            \"min\" : [\n" +
                "                -50,\n" +
                "                0,\n" +
                "                -50\n" +
                "            ],\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 13,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 14,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 15,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"max\" : [\n" +
                "                50,\n" +
                "                0,\n" +
                "                50\n" +
                "            ],\n" +
                "            \"min\" : [\n" +
                "                -50,\n" +
                "                0,\n" +
                "                -50\n" +
                "            ],\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 16,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 17,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"bufferViews\" : [\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 72,\n" +
                "            \"byteOffset\" : 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 72,\n" +
                "            \"byteOffset\" : 72\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 144\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 12,\n" +
                "            \"byteOffset\" : 192\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 144,\n" +
                "            \"byteOffset\" : 204\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 144,\n" +
                "            \"byteOffset\" : 348\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 96,\n" +
                "            \"byteOffset\" : 492\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 24,\n" +
                "            \"byteOffset\" : 588\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 612\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 660\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 32,\n" +
                "            \"byteOffset\" : 708\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 12,\n" +
                "            \"byteOffset\" : 740\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 752\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 800\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 32,\n" +
                "            \"byteOffset\" : 848\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 880\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 928\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 32,\n" +
                "            \"byteOffset\" : 976\n" +
                "        }\n" +
                "    ],\n" +
                "    \"buffers\" : [\n" +
                "        {\n" +
                "            \"byteLength\" : 1008,\n" +
                "            \"uri\" : \"" + poi + "_LOD00.bin\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        
        return output;
    }
    
}
