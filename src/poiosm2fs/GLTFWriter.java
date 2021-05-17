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
        
        /* String to write into gltf files, simplest solution */
        
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
                "                10,\n" +
                "                11,\n" +
                "                12,\n" +
                "                13\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"nodes\" : [\n" +
                "        {\n" +
                "            \"mesh\" : 0,\n" +
                "            \"name\" : \"arrow_down\",\n" +
                "            \"rotation\" : [\n" +
                "                -1,\n" +
                "                0,\n" +
                "                0,\n" +
                "                1.910685676922942e-15\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                0.3876168131828308,\n" +
                "                1,\n" +
                "                0.3876168131828308\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                -27,\n" +
                "                0\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 1,\n" +
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
                "            \"mesh\" : 2,\n" +
                "            \"name\" : \"billboard_1\",\n" +
                "            \"rotation\" : [\n" +
                "                0.5,\n" +
                "                -0.5,\n" +
                "                0.5,\n" +
                "                0.4999999701976776\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                12,\n" +
                "                0.10000000149011612\n" +
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
                "                    \"intensity\" : 100,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_a\",\n" +
                "            \"rotation\" : [\n" +
                "                0.46103471517562866,\n" +
                "                0.5361407995223999,\n" +
                "                0.5361407995223999,\n" +
                "                0.4610348343849182\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                8,\n" +
                "                35.079715728759766\n" +
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
                "                    \"intensity\" : 100,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_b\",\n" +
                "            \"rotation\" : [\n" +
                "                0.46103471517562866,\n" +
                "                0.5361407995223999,\n" +
                "                0.5361407995223999,\n" +
                "                0.4610348343849182\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                8,\n" +
                "                -35.0797004699707\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 3,\n" +
                "            \"name\" : \"billboard_2\",\n" +
                "            \"rotation\" : [\n" +
                "                0.7071067690849304,\n" +
                "                0,\n" +
                "                0,\n" +
                "                0.7071067690849304\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                3,\n" +
                "                0.10000000149011612\n" +
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
                "                    \"intensity\" : 100,\n" +
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
                "                0.7051095962524414,\n" +
                "                0.7051095962524414,\n" +
                "                0.053108006715774536,\n" +
                "                -0.05310794711112976\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                -3.6689376831054688,\n" +
                "                35.079715728759766\n" +
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
                "                    \"intensity\" : 100,\n" +
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
                "                0.7051095962524414,\n" +
                "                0.7051095962524414,\n" +
                "                0.053108006715774536,\n" +
                "                -0.05310794711112976\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                25,\n" +
                "                -3.6689376831054688,\n" +
                "                35.079715728759766\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 4,\n" +
                "            \"name\" : \"billboard_3\",\n" +
                "            \"rotation\" : [\n" +
                "                0,\n" +
                "                0.7071067690849304,\n" +
                "                -0.7071067690849304,\n" +
                "                -3.0908619663705394e-08\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                3,\n" +
                "                0.10000000149011612\n" +
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
                "                    \"intensity\" : 100,\n" +
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
                "                -0.053108036518096924,\n" +
                "                0.053107887506484985,\n" +
                "                0.7051095962524414,\n" +
                "                0.7051095962524414\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                -3.6689376831054688,\n" +
                "                -35.0797004699707\n" +
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
                "                    \"intensity\" : 100,\n" +
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
                "                -0.053108036518096924,\n" +
                "                0.053107887506484985,\n" +
                "                0.7051095962524414,\n" +
                "                0.7051095962524414\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                25,\n" +
                "                -3.6689376831054688,\n" +
                "                -35.0797004699707\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"mesh\" : 5,\n" +
                "            \"name\" : \"billboard_4\",\n" +
                "            \"rotation\" : [\n" +
                "                0.5,\n" +
                "                0.5,\n" +
                "                -0.5,\n" +
                "                0.4999999701976776\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1.0240000486373901,\n" +
                "                1,\n" +
                "                0.06400000303983688\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                0,\n" +
                "                12,\n" +
                "                0.10000000149011612\n" +
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
                "                    \"intensity\" : 100,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_a.004\",\n" +
                "            \"rotation\" : [\n" +
                "                0.46103471517562866,\n" +
                "                0.5361407995223999,\n" +
                "                0.5361407995223999,\n" +
                "                0.4610348343849182\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                8,\n" +
                "                35.079715728759766\n" +
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
                "                    \"intensity\" : 100,\n" +
                "                    \"cone_angle\" : 360,\n" +
                "                    \"has_simmetry\" : false,\n" +
                "                    \"flash_frequency\" : 0,\n" +
                "                    \"flash_duration\" : 0,\n" +
                "                    \"flash_phase\" : 0,\n" +
                "                    \"rotation_speed\" : 0,\n" +
                "                    \"day_night_cycle\" : false\n" +
                "                }\n" +
                "            },\n" +
                "            \"name\" : \"light_b.004\",\n" +
                "            \"rotation\" : [\n" +
                "                0.46103471517562866,\n" +
                "                0.5361407995223999,\n" +
                "                0.5361407995223999,\n" +
                "                0.4610348343849182\n" +
                "            ],\n" +
                "            \"scale\" : [\n" +
                "                1,\n" +
                "                1,\n" +
                "                28.32537078857422\n" +
                "            ],\n" +
                "            \"translation\" : [\n" +
                "                -25,\n" +
                "                8,\n" +
                "                -35.0797004699707\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"materials\" : [\n" +
                "        {\n" +
                "            \"emissiveFactor\" : [\n" +
                "                3,\n" +
                "                2.8514654636383057,\n" +
                "                1.6179780960083008\n" +
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
                "                0,\n" +
                "                0,\n" +
                "                0\n" +
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
                "            \"name\" : \"arrow_down\",\n" +
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
                "            \"name\" : \"base\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 4,\n" +
                "                        \"NORMAL\" : 5,\n" +
                "                        \"TEXCOORD_0\" : 6\n" +
                "                    },\n" +
                "                    \"indices\" : 7,\n" +
                "                    \"material\" : 0\n" +
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
                "                    \"material\" : 1\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_2\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 12,\n" +
                "                        \"NORMAL\" : 13,\n" +
                "                        \"TEXCOORD_0\" : 14\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 1\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_3\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 15,\n" +
                "                        \"NORMAL\" : 16,\n" +
                "                        \"TEXCOORD_0\" : 17\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 1\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\" : \"billboard_4\",\n" +
                "            \"primitives\" : [\n" +
                "                {\n" +
                "                    \"attributes\" : {\n" +
                "                        \"POSITION\" : 18,\n" +
                "                        \"NORMAL\" : 19,\n" +
                "                        \"TEXCOORD_0\" : 20\n" +
                "                    },\n" +
                "                    \"indices\" : 11,\n" +
                "                    \"material\" : 1\n" +
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
                "            \"count\" : 12,\n" +
                "            \"max\" : [\n" +
                "                0.8660253882408142,\n" +
                "                25,\n" +
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
                "            \"bufferView\" : 1,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 2,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 3,\n" +
                "            \"componentType\" : 5123,\n" +
                "            \"count\" : 12,\n" +
                "            \"type\" : \"SCALAR\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 4,\n" +
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
                "            \"bufferView\" : 5,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 6,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 6,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 7,\n" +
                "            \"componentType\" : 5123,\n" +
                "            \"count\" : 6,\n" +
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
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 18,\n" +
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
                "            \"bufferView\" : 19,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bufferView\" : 20,\n" +
                "            \"componentType\" : 5126,\n" +
                "            \"count\" : 4,\n" +
                "            \"type\" : \"VEC2\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"bufferViews\" : [\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 144,\n" +
                "            \"byteOffset\" : 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 144,\n" +
                "            \"byteOffset\" : 144\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 96,\n" +
                "            \"byteOffset\" : 288\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 24,\n" +
                "            \"byteOffset\" : 384\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 72,\n" +
                "            \"byteOffset\" : 408\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 72,\n" +
                "            \"byteOffset\" : 480\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 552\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 12,\n" +
                "            \"byteOffset\" : 600\n" +
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
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 1008\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 48,\n" +
                "            \"byteOffset\" : 1056\n" +
                "        },\n" +
                "        {\n" +
                "            \"buffer\" : 0,\n" +
                "            \"byteLength\" : 32,\n" +
                "            \"byteOffset\" : 1104\n" +
                "        }\n" +
                "    ],\n" +
                "    \"buffers\" : [\n" +
                "        {\n" +
                "            \"byteLength\" : 1136,\n" +
                "            \"uri\" : \"" + poi + ".bin\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        
        return output;
    }
    
}
